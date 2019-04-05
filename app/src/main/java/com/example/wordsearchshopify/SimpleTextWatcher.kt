package com.example.wordsearchshopify

import android.text.Editable
import android.text.TextWatcher

class SimpleTextWatcher(val listener: (String) -> Unit) : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        listener(s.toString())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}