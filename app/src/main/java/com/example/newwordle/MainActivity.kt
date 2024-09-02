package com.example.newwordle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var guessCounter = 0
    private var streakCounter = 0 // To keep track of the streak
    private lateinit var wordSource: FourLetterWordList
    private lateinit var correctWord: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wordSource = FourLetterWordList
        correctWord = wordSource.getRandomFourLetterWord()

        val submitButton = findViewById<Button>(R.id.guessButton)
        val resetButton = findViewById<Button>(R.id.resetButton)
        val guess1 = findViewById<TextView>(R.id.guess1)
        val guess2 = findViewById<TextView>(R.id.guess2)
        val guess3 = findViewById<TextView>(R.id.guess3)
        val guess1Check = findViewById<TextView>(R.id.guess1Check)
        val guess2Check = findViewById<TextView>(R.id.guess2Check)
        val guess3Check = findViewById<TextView>(R.id.guess3Check)
        val correctWordView = findViewById<TextView>(R.id.correctWord)
        val userGuess = findViewById<EditText>(R.id.userGuess)
        val streakView = findViewById<TextView>(R.id.streakView) // Display streak

        // Update the streak view with initial value
        updateStreakView()

        submitButton.setOnClickListener {
            val textEntered = userGuess.text.toString().uppercase()
            userGuess.setText("")

            if (textEntered.length != 4 || !isLetters(textEntered)) {
                val toast = Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 10)
                toast.show()
                return@setOnClickListener
            }

            val userGuessCheck = checkGuess(textEntered, correctWord)
            guessCounter++

            when (guessCounter) {
                1 -> {
                    guess1.text = textEntered
                    guess1Check.text = userGuessCheck
                }
                2 -> {
                    guess2.text = textEntered
                    guess2Check.text = userGuessCheck
                }
                3 -> {
                    guess3.text = textEntered
                    guess3Check.text = userGuessCheck
                }
            }

            if (textEntered == correctWord) {
                displayWinMessage(submitButton, userGuess, correctWordView, resetButton)
            } else if (guessCounter == 3) {
                displayLossMessage(submitButton, userGuess, correctWordView, resetButton)
            }
        }

        resetButton.setOnClickListener {
            resetButton.visibility = View.INVISIBLE
            guessCounter = 0
            correctWord = wordSource.getRandomFourLetterWord()

            guess1.text = ""
            guess1Check.text = ""
            guess2.text = ""
            guess2Check.text = ""
            guess3.text = ""
            guess3Check.text = ""

            submitButton.isEnabled = true
            userGuess.isEnabled = true
            correctWordView.visibility = View.INVISIBLE
        }
    }

    private fun checkGuess(guess: String, wordToGuess: String): String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            } else if (guess[i] in wordToGuess) {
                result += "+"
            } else {
                result += "X"
            }
        }
        return result
    }

    private fun isLetters(string: String): Boolean {
        return string.matches("^[a-zA-Z]*$".toRegex())
    }

    private fun displayWinMessage(submitButton: Button, userGuess: EditText, correctWordView: TextView, resetButton: Button) {
        val toast = Toast.makeText(this, getString(R.string.win), Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 10)
        toast.show()
        submitButton.isEnabled = false
        userGuess.isEnabled = false
        correctWordView.text = correctWord
        correctWordView.visibility = View.VISIBLE
        resetButton.visibility = View.VISIBLE
        streakCounter++ // Increase streak count
        updateStreakView()
    }

    private fun displayLossMessage(submitButton: Button, userGuess: EditText, correctWordView: TextView, resetButton: Button) {
        correctWordView.text = correctWord
        correctWordView.visibility = View.VISIBLE
        submitButton.isEnabled = false
        userGuess.isEnabled = false
        resetButton.visibility = View.VISIBLE
        streakCounter = 0 // Reset streak count on loss
        updateStreakView()
    }

    private fun updateStreakView() {
        val streakView = findViewById<TextView>(R.id.streakView)
        streakView.text = "Streak: $streakCounter"
    }
}
