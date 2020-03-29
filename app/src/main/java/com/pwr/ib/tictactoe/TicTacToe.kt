package com.pwr.ib.tictactoe

import com.pwr.ib.tictactoe.Mark.*
import java.lang.Exception
import java.lang.NumberFormatException

class TicTacToe {

    //plansza w postaci tablicy
    private var board: Array<Array<Mark>> = arrayOf()
    //rozmiar planszy to 3x3
    private val boardSize = 3
    //symbol aktualnego gracza
    private var currentPlayerMark = X
    private var hasGameEnded = false

    //inicjalizacja pustej planszy
    init {
        makeBoardEmpty()
    }

    //tablica o ustalonym wymiarze zostaje wypelniona pustymi miejscami
    private fun makeBoardEmpty() {
        this.board = Array(boardSize) { Array(boardSize) { Mark.EMPTY } }
    }

    //wyswietlanie odpowiednio sformatowanej planszy przy uzyciu dwoch petli for
    private fun printBoard() {
        println("\n-------------------")
        for (i in 0 until boardSize) {
            print("|")
            for (j in 0 until boardSize) {
                print(String.format("%3s  |", board[i][j].str))
            }
            println("\n-------------------")
        }
    }

    //na wykonanie ruchu sklada się sprawdzenie czy pole jest wolne, zmiana gracza,
    // aktualizacja planszy, jej wyswietlenie i zwrócenie informacji  o aktualnym symbolu gracza,
    //następnym graczu i komunikacie kónca gry
    fun makeMove(
        i: Int,
        j: Int
    ): Array<String> {
        isFieldAvailable(i, j)
        changePlayer()
        updateBoard(i, j)
        printBoard()
        val endMessage = checkIfEnd()
        val currentPlayerSymbol = currentPlayerMark.str
        val nextPlayerSymbol = currentPlayerMark.opposite
        return arrayOf(currentPlayerSymbol, nextPlayerSymbol, endMessage)
    }

    //zmiana aktualnego gracza z O na X lub z X na O
    private fun changePlayer() {
        currentPlayerMark = if (currentPlayerMark == X) O else X
    }


    //metoda sprawdzajaca zakres wprowadzonych danych oraz to czy wybrane pole na planszy jest puste
    private fun isFieldAvailable(i: Int, j: Int): Boolean {
        if (i !in 0..2 || j !in 0..2) throw NumberFormatException()
        if (board[i][j] != EMPTY) throw Exception("This space is already taken")
        return true
    }

    //metoda aktualizujace plansze
    private fun updateBoard(i: Int, j: Int) {
        board[i][j] = currentPlayerMark
    }

    //metoda sprawdzajaca czy gra sie skonczyla przez zajecie wszystkich pol
    //lub przez wygrana w kolumnie, rzedzie lub na ukos
    private fun checkIfEnd(): String {

        if (wonInColumnsOrRows() != "") {
            hasGameEnded = true
            return wonInColumnsOrRows()
        }

        if (areAllSpacesTaken() != "") {
            hasGameEnded = true
            return areAllSpacesTaken()
        }

        return ""
    }

    //metoda sprawdzajaca czy istnieje puste pole
    private fun areAllSpacesTaken(): String {
        if (!board.flatten().contains(EMPTY)) {
            return ("Draw")
        }
        return ""
    }

    //metoda sprawdzająca czy doszlo do wygranej w chociaz jednej kolumnie, rzedzie lub przekatnej
    private fun wonInColumnsOrRows(): String {
        val diagonalRight = mutableListOf<Mark>()
        val diagonalLeft = mutableListOf<Mark>()
        for (i in 0 until boardSize) {
            val row = mutableListOf<Mark>()
            val column = mutableListOf<Mark>()
            diagonalRight.add(board[i][i])
            diagonalLeft.add(board[i][boardSize - i - 1])
            for (j in 0 until boardSize) {
                row.add(board[i][j])
                column.add(board[j][i])
            }
            if (areAllSameMarks(row) || areAllSameMarks(column)) {
                println("areAllSameMarks(row) " + areAllSameMarks(row))
                println("areAllSameMarks(column) " + areAllSameMarks(column))
                return ("Player $currentPlayerMark won")
            }
        }
        if (areAllSameMarks(diagonalLeft) || areAllSameMarks(diagonalRight)) {
            println("areAllSameMarks(diagonalLeft) " + areAllSameMarks(diagonalLeft))
            println("areAllSameMarks(diagonalRight) " + areAllSameMarks(diagonalRight))
            return ("Player $currentPlayerMark won")
        }
        return ""
    }

    //metoda sprawdzajaca czy wszystkie symbole w liscie sa identyczne
    private fun areAllSameMarks(list: List<Mark>): Boolean {
        for (mark in list) {
            if (mark != list[0] || mark == EMPTY)
                return false
        }
        return true
    }


}