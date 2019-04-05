package com.example.wordsearchshopify

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import java.lang.StringBuilder
import kotlin.math.sqrt

class MainViewModel : ViewModel() {

    private val _gridLettersLiveData = MutableLiveData<List<Letter>>()
    val gridLettersLiveData: LiveData<List<Letter>> = _gridLettersLiveData
    private val _gameCompleteLiveData = MutableLiveData<Boolean>()
    val gameCompleteLiveData: LiveData<Boolean> = _gameCompleteLiveData

    private var arrayCapacity = 100
    private val words = listOf("swift", "kotlin", "objectivec", "variable", "java", "mobile")
    private val letters = listOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
        'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')

    init {
        shuffleWords()
    }

    fun onUiEvent(uiEvent: UIEvent) {
        when (uiEvent) {
            UIEvent.BOARD_SHUFFLE_CLICK -> shuffleWords()
            is UIEvent.ON_LETTER_CLICK -> onLetterClicked(uiEvent.index)
        }
    }

    private fun shuffleWords() {
        val listOfLetters = arrayListOf<Letter>()
        for (i in 0..(arrayCapacity - 1)) {
            listOfLetters.add(Letter(letters.random(), isSelected = false, isFound = false))
        }
        val unusedRows = (0..9).toMutableList()
        words.forEach {
            val row = unusedRows.random()
            val initCol = (0..(9 - it.length + 1)).random()
            it.forEachIndexed { index, char ->
                val col = row * 10 + initCol + index
                listOfLetters[col] = Letter(char, isSelected = false, isFound = false)
            }
            unusedRows.remove(row)
        }
        _gridLettersLiveData.postValue(listOfLetters)
    }

    private fun onLetterClicked(index: Int) {
        val updatedLetters = _gridLettersLiveData.value?.toMutableList()?.apply {
            this[index] = Letter(this[index].letter, !this[index].isSelected, false)
        }
        _gridLettersLiveData.postValue(updatedLetters?.let { validateWords(it) })
    }

    private fun validateWords(lettersToValidate: MutableList<Letter>): List<Letter> {
        val curWord = StringBuilder()
        var wordsFound = 0
        (0..9).forEach rowLoop@{  row ->
            curWord.clear()
            (0..9).forEach colLoop@{ col ->
                val letter = lettersToValidate[row * 10 + col]
                if (letter.isSelected) {
                    curWord.append(lettersToValidate[row * 10 + col].letter)
                } else if (!curWord.isEmpty()) {
                    return@colLoop
                }

                if (words.contains(curWord.toString())) {
                    ++wordsFound
                    (0..col).reversed().forEach {
                        val index = row * 10 + it
                        if (lettersToValidate[index].isSelected) {
                            lettersToValidate[index] = lettersToValidate[index].copy(isFound = true)
                        } else {
                            return@forEach
                        }
                    }
                    return@colLoop
                }
            }
        }
        if (wordsFound == words.size) {
            _gameCompleteLiveData.postValue(true)
        }

        return lettersToValidate
    }
}