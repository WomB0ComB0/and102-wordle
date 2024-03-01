package com.example.wordle

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.jinatonic.confetti.CommonConfetti

class MainActivity : AppCompatActivity() {
    private var wordToGuess = FourLetterWordList.getRandomFourLetterWord()
    private var remainingAttempts = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val guessEditText: EditText = findViewById(R.id.WordInput)
        val submitButton: Button = findViewById(R.id.button)
        val textView: TextView = findViewById(R.id.textView)
        val guessCount: TextView  = findViewById(R.id.GuessCount)
        val guessWord_AndCorrectness: TextView = findViewById(R.id.GuessedWord_and_Correctness)

        println(wordToGuess)
        textView.visibility = View.GONE
        submitButton.setOnClickListener {
            val guess = guessEditText.text.toString().uppercase()
            var guessNumber = 0
            guessCount.append("Guess $guessNumber: $guess")
            guessWord_AndCorrectness.text = guessCount.toString()

            val correctness = checkGuess(guess)
            val correctnessText = "Guess $guessNumber Check: $correctness"
            guessWord_AndCorrectness.append("\n$correctnessText")

            remainingAttempts--
            guessNumber++

            if (guess == wordToGuess) {
                Toast.makeText(this, "Congratulations! You guessed the word!", Toast.LENGTH_SHORT).show()
                CommonConfetti.rainingConfetti(
                    findViewById(R.id.ConfettiContainer),
                    intArrayOf(android.R.color.black)
                ).infinite()
                submitButton.isEnabled = false
            } else if (remainingAttempts == 0) {
                textView.visibility = View.VISIBLE // Show the textView when attempts are 0
                textView.append(wordToGuess)
                Toast.makeText(this, "Out of attempts. The word was: $wordToGuess", Toast.LENGTH_SHORT).show()
                submitButton.isEnabled = false
            } else {
                Toast.makeText(this, "Incorrect guess. $remainingAttempts attempts remaining.", Toast.LENGTH_SHORT).show()
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


