package com.example.wordsearchshopify

sealed class UIEvent {
    object BOARD_SHUFFLE_CLICK : UIEvent()
    class ON_LETTER_CLICK(val index: Int) : UIEvent()
}
