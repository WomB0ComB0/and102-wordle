package com.example.wordle

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var wordToGuess = FourLetterWordList.getRandomFourLetterWord()
    private var remainingAttempts = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val guessEditText: EditText = findViewById(R.id.et_simple)
        val submitButton: Button = findViewById(R.id.button)
        val textView: TextView = findViewById(R.id.textView)

        submitButton.setOnClickListener {
            val guess = guessEditText.text.toString().uppercase()
            if (remainingAttempts > 0) {
                val correctness = checkGuess(guess)
                textView.text = "Correctness: $correctness"
                remainingAttempts--
                if (guess == wordToGuess) {
                    Toast.makeText(this, "Congratulations! You guessed the word!", Toast.LENGTH_SHORT).show()
                    submitButton.isEnabled = false
                } else if (remainingAttempts == 0) {
                    textView.append("\n\nOut of attempts. The word was: $wordToGuess")
                    submitButton.isEnabled = false
                } else {
                    Toast.makeText(this, "Incorrect guess. $remainingAttempts attempts remaining.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Out of attempts. The word was: $wordToGuess", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkGuess(guess: String): String {
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
}
