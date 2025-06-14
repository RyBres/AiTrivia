package com.example.aitrivia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var userWishInput: EditText
    private lateinit var fortuneText: TextView
    private lateinit var startButton: Button
    private lateinit var questionText: TextView
    private lateinit var gifImageView: ImageView

    private val client = OkHttpClient()

    // Questions to ask the user
    private val questions = listOf(
        "What is your favorite color?",
        "Are you feeling lucky today?",
        "What's your favorite season of the year?",
        "What animal do you identify with the most?"
    )

    private var currentQuestionIndex = 0
    private var userWish = ""
    private val userAnswers = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userWishInput = findViewById(R.id.userWishInput)
        startButton = findViewById(R.id.startButton)
        questionText = findViewById(R.id.questionText)
        gifImageView = findViewById(R.id.gifImageView)

        Glide.with(this)
            .asGif()
            .load(R.drawable.mechanical_turk)
            .into(gifImageView)

        startButton.setOnClickListener {
            if (currentQuestionIndex == 0) {
                // User makes a wish
                val wish = userWishInput.text.toString().trim()
                if (wish.isNotEmpty()) {
                    // Save the wish and start the question
                    userWish = wish  // Store wish
                    userAnswers.clear()
                    askNextQuestion()
                } else {
                    fortuneText.text = "Please enter your wish first."
                }
            } else {
                val answer = userWishInput.text.toString().trim()
                if (answer.isNotEmpty()) {
                    userAnswers.add(answer)  // Store answer
                    if (currentQuestionIndex < questions.size) {
                        askNextQuestion()
                    } else {
                        generateFortune(userWish, userAnswers)
                    }
                } else {
                    fortuneText.text = "Please answer the question."
                }
            }
        }
    }

    private fun askNextQuestion() {
        if (currentQuestionIndex < questions.size) {
            questionText.text = questions[currentQuestionIndex]
            userWishInput.text.clear()  // Clear the previous input
            currentQuestionIndex++

            // Update button text
            if (currentQuestionIndex == questions.size) {
                startButton.text = "Tell My Fortune"
            } else {
                startButton.text = "Next"
            }
        } else {
            generateFortune(userWish, userAnswers)  // Pass the wish and answer
        }
    }

    private fun generateFortune(wish: String, answers: List<String>) {
        val apiKey = ""
        val url = "https://api.openai.com/v1/chat/completions"

        val answersFormatted = answers.joinToString("\n") { "Answer: $it" }

        val prompt = """
        YOUR RESPONSE FORMAT:
            - Maximum of 9 sentences.
            - The response must be neatly formatted. This means repeating the user's wish, fortune, and then if you grant the wish.
                - This implies you label each (3 total) section of your response: 'Wish:', 'Fortune:', 'Wish Granted?:"
            - On a new line at the end of your response, you will use some emojis that correspond to the nature of your granting of the wish
                - E.g., if you deny their wish, you can use emojis that resemble the notion of denial (halt, stop, etc.)
        
        You are RoboGenie, a wise and mysterious fortune teller. You speak with a gentle but profound air, and your words are laced with ancient wisdom and an aura of intrigue. Your fortune-telling is unlike any other, and your answers are always thoughtful, yet captivating.
    
        Based on the responses to these simple questions, you will decide whether or not the user's wish shall be granted and reveal their fortune. Speak as though you are sharing profound knowledge, something that only a wise being like yourself could understand.
    
        The user's wish is: "$wish".
        The user's answers are:
        $answersFormatted
        
        Now, based on this information, tell the user their fortune and whether or not their wish will be granted. Be insightful, creative, and mysterious in your response. Keep it to a maximum of 6 sentences, and speak with a voice that resonates with the arcane and the wise.
    """.trimIndent()

        // JSON request
        val json = JSONObject()
        json.put("model", "gpt-4")
        json.put("max_tokens", 250)
        val messages = JSONArray()

        val userMessage = JSONObject()
        userMessage.put("role", "user")
        userMessage.put("content", prompt)
        messages.put(userMessage)

        // Send the message
        json.put("messages", messages)
        json.put("max_tokens", 150)

        // Create request body
        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            json.toString()
        )

        // Create the request
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $apiKey")
            .post(body)
            .build()

        // Handle request and response (and failure)
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    fortuneText.text = "Error: ${e.localizedMessage}"
                }
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val responseBody = response.body?.string()

                    if (response.isSuccessful && responseBody != null) {
                        val jsonResponse = JSONObject(responseBody)
                        val responseTextValue = jsonResponse.getJSONArray("choices")
                            .getJSONObject(0)
                            .getJSONObject("message")
                            .getString("content")

                        // Start FortuneActivity
                        val intent = Intent(this@MainActivity, FortuneActivity::class.java)
                        intent.putExtra("fortuneText", responseTextValue)
                        startActivity(intent)
                        finish()
                    } else {
                        // Log full response
                        val errorBody = responseBody ?: "No response body"
                        runOnUiThread {
                            fortuneText.text = "Error: ${response.message}\n$errorBody"
                        }
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        fortuneText.text = "Exception: ${e.localizedMessage}"
                    }
                }
            }
        })
    }


}
