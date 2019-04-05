package com.example.wordsearchshopify

import android.support.v7.util.DiffUtil

data class Letter(val letter: Char, val isSelected: Boolean, val isFound: Boolean) {
    companion object {
        // https://issuetracker.google.com/issues/37007605#c10
        // These have to be false to avoid the error in the Android framework linked above
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Letter>() {
            override fun areItemsTheSame(l1: Letter, l2: Letter) = false

            override fun areContentsTheSame(l1: Letter, l2: Letter) = false
        }
    }
}