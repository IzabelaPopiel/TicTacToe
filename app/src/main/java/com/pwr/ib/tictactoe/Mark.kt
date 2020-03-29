package com.pwr.ib.tictactoe

enum class Mark(val str: String, val opposite: String) {
    O("O", "X"),
    X("X","O"),
    EMPTY("-", "-")
}
