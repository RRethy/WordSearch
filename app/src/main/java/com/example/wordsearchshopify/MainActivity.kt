package com.example.wordsearchshopify

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val gridAdapter by lazy { GridAdapter {
        if (!gameCompleted) viewModel.onUiEvent(UIEvent.ON_LETTER_CLICK(it))
    }}
    private val viewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }
    private var gameCompleted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        setupObservables()
        shuffleButton.setOnClickListener { shuffleBoard() }
    }

    private fun shuffleBoard() {
        gameCompleted = false
        viewModel.onUiEvent(UIEvent.BOARD_SHUFFLE_CLICK)
    }

    private fun setupRecyclerView() {
        grid.layoutManager = GridLayoutManager(this, 10)
        grid.itemAnimator = null
        grid.adapter = gridAdapter
    }

    private fun setupObservables() {
        viewModel.gridLettersLiveData.observe(this, Observer {
            gridAdapter.submitList(it)
        })
        viewModel.gameCompleteLiveData.observe(this, Observer {
            gameCompleted = true
            Toast.makeText(this, R.string.congrats, Toast.LENGTH_LONG).show()
        })
    }
}
