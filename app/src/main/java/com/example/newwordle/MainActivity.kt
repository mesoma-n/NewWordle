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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val wordSource = FourLetterWordList
        val submitButton = findViewById<Button>(R.id.guessButton)
        val resetButton = findViewById<Button>(R.id.resetButton)
        val guess1 =  findViewById<TextView>(R.id.guess1)
        val guess2 =  findViewById<TextView>(R.id.guess2)
        val guess3 =  findViewById<TextView>(R.id.guess3)
        val guess1Check = findViewById<TextView>(R.id.guess1Check)
        val guess2Check = findViewById<TextView>(R.id.guess2Check)
        val guess3Check = findViewById<TextView>(R.id.guess3Check)
        val correctWordView = findViewById<TextView>(R.id.correctWord)
        val userGuess = findViewById<EditText>(R.id.userGuess)
        var correctWord = wordSource.getRandomFourLetterWord()

        submitButton.setOnClickListener {
            val textEntered = userGuess.text
            userGuess.setText("")
            val userGuessCheck = checkGuess(textEntered.toString().uppercase(), correctWord)
            guessCounter++
            val alpha = isLetters(textEntered.toString())

            if (guessCounter == 1) {
                if (textEntered.toString().length != 4 || !alpha){
                    val toast = Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 10)
                    toast.show()
                    guessCounter--
                }
                else{
                    guess1.text = textEntered.toString().uppercase()
                    guess1Check.text = userGuessCheck
                }
            }
            else if (guessCounter == 2) {
                if (textEntered.toString().length != 4 || !alpha) {
                    val toast = Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 10)
                    toast.show()
                    guessCounter--
                }
                else {
                    guess2.text = textEntered.toString().uppercase()
                    guess2Check.text = userGuessCheck
                }
            }
            else if (guessCounter == 3) {
                if (textEntered.toString().length != 4 || !alpha) {
                    val toast = Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 10)
                    toast.show()
                    guessCounter--
                }
                else {
                    guess3.text = textEntered.toString().uppercase()
                    guess3Check.text = userGuessCheck
                }
            }

            if (textEntered.toString().uppercase() == correctWord) {
                val toast = Toast.makeText(this, getString(R.string.win), Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 10)
                toast.show()
                submitButton.isEnabled = false
                userGuess.isEnabled = false
                correctWordView.text = correctWord
                correctWordView.visibility = View.VISIBLE
                resetButton.visibility = View.VISIBLE
            } else if (guessCounter == 3){
                correctWordView.text = correctWord
                correctWordView.visibility = View.VISIBLE
                submitButton.isEnabled = false
                userGuess.isEnabled = false
                resetButton.visibility = View.VISIBLE
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
    private fun checkGuess(guess: String, wordToGuess:String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }
    private fun isLetters(string: String): Boolean {
        return string.matches("^[a-zA-Z]*$".toRegex())
    }
}