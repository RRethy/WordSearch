package com.example.wordsearchshopify

import android.support.v4.content.ContextCompat
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_holder_letter.*

class GridAdapter(val clickListener: (Int) -> Unit) : ListAdapter<Letter, GridAdapter.LetterViewHolder>(Letter.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterViewHolder {
        return LetterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_letter, parent, false))
    }

    override fun onBindViewHolder(viewHolder: LetterViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }

    inner class LetterViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            letterText.setOnClickListener {
                if (!getItem(adapterPosition).isFound) {
                    clickListener(adapterPosition)
                }
            }
        }

        fun bind(letter: Letter) {
            when {
                letter.isFound -> {
                    letterText.background = ContextCompat.getDrawable(containerView.context, R.drawable.green_circle)
                }
                letter.isSelected -> {
                    letterText.background = ContextCompat.getDrawable(containerView.context, R.drawable.blue_circle)
                }
                else -> letterText.setBackgroundResource(0)
            }
            letterText.text = letter.letter.toString()
        }
    }
}