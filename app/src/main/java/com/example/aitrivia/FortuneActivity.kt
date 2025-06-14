package com.example.aitrivia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FortuneActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fortune)

        val fortuneText = findViewById<TextView>(R.id.fortuneDisplay)
        val backButton = findViewById<Button>(R.id.backButton)

        // Get the fortune passed
        val fortune = intent.getStringExtra("fortuneText")

        // Display the fortune
        fortuneText.text = fortune ?: "No fortune generated."

        // button
        backButton.setOnClickListener {
            // Clear data and go back to the main screen
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
