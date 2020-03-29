package com.pwr.ib.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    //Dwuwymiarowa tablica przechowujaca przyciski tworzace plansze do gry
    private var fields: Array<Array<Button?>> = Array(3) { arrayOfNulls<Button>(3) }
    private lateinit var textCurrentPlayer: TextView
    private lateinit var ticTacToe: TicTacToe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ticTacToe = TicTacToe()
        textCurrentPlayer = findViewById(R.id.textNextPlayer)
        textCurrentPlayer.text = resources.getString(R.string.next_player) + " " + Mark.O
        createBoardOfButtons()
    }

    //Organizuje przyciski tworzące planszę w dwuwymiarową tablicę.
    //Każdemu z przycisków przypisuje metodę wywoływaną przy jego naciśnięciu.
    private fun createBoardOfButtons() {
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                val resID = resources.getIdentifier("button_$i$j", "id", packageName)
                fields[i][j] = findViewById<Button>(resID)
                fields[i][j]?.setOnClickListener { buttonClicked(i, j) }
            }
        }
    }


    //Metoda wywoływana po wciśnięciu przycisku.
    //Wykonuje ruch na planszy dla danych wspołrzędnych
    //Na podstawie zwroconych danych zostaje na wciśniętym przycisku zostaje wyświetlony symbol
    //O lub X. Zostaje uakutalniona informacja o tym, który gracz ma wykonać następny ruch.
    //Jeśli została otrzymana wiadomość o zakonczeniu gry to zostaje wyświetlone okno z odpowiednim
    //komunikatem.

    private fun buttonClicked(i: Int, j: Int) {
        try {
            val afterMoveValues =
                ticTacToe.makeMove(i, j)
            val currentPlayer = afterMoveValues[0]
            val nextPlayer = afterMoveValues[1]
            val endMessage = afterMoveValues[2]
            fields[i][j]?.text = currentPlayer
            textCurrentPlayer.text =
                "${resources.getString(R.string.next_player)} $nextPlayer"
            if (endMessage != "") {
                showEndDialog(endMessage)
            }

        } catch (ex: Exception) {
            Toast.makeText(this@MainActivity, ex.message, Toast.LENGTH_SHORT).show()
        }
    }

    //Metoda wywoływana po wciśnięciu przycisku reset
    fun resetClicked(view: View) {
        resetGame()
    }

    //Metoda resetujaca grę. Zostaje stworzony nowy obiekt klasy TicTacToe. Jako pierwszy gracz
    //Zostaje ustawione kolko O. Wyszstykie przyciski zostają wyczyszczone i odblokowane.
    private fun resetGame() {
        ticTacToe = TicTacToe()
        textCurrentPlayer.text = "${resources.getString(R.string.next_player)} ${Mark.O}"
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                fields[i][j]?.text = ""
                fields[i][j]?.isEnabled = true
            }
        }
    }

    //Metoda wyswietlajaca komunikat o zakonczeniu gry. Po wcisnieciu 'nowa gra' gra zostaje
    //zresetowana. Po wcisnieiu 'anuluj' okno zamyka sie, a przyciski na planszy zostaja zablokowane.
    private fun showEndDialog(endMessage: String) {
        val mAlertDialog = AlertDialog.Builder(this@MainActivity)
        mAlertDialog.setTitle("The end")
        mAlertDialog.setMessage(endMessage)
        mAlertDialog.setCancelable(false)
        mAlertDialog.setPositiveButton("New game") { dialog, id ->
            resetGame()
        }
        mAlertDialog.setNegativeButton("Cancel") { dialog, id ->
            disableButtons()
        }
        mAlertDialog.show()
    }

    //Metoda blokujaca dzialanie przyciskow tworzacych plansze do gry.
    private fun disableButtons() {
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                fields[i][j]?.isEnabled = false
            }
        }
    }

}
