# 🤖🧞 RoboGenie

![RoboGenie Demo](demo.gif) <!-- Replace with your actual demo.gif or MP4 hosted in the repo -->

RoboGenie is an Android app that blends whimsical fortune-telling with the power of artificial intelligence. It offers users a fun, mysterious, and personalized experience powered by the GPT-4 model from OpenAI. Make a wish, answer a few quirky questions, and receive a captivating fortune from the enigmatic RoboGenie.

---

## ✨ Features

- 🎭 **Interactive Fortune-Telling**: Users make a wish and answer a series of playful personality questions.
- 🤖 **AI-Powered Wisdom**: Responses are generated via the OpenAI GPT-4 API using a carefully crafted, character-rich prompt.
- 🎨 **Stylized UI**: A simple but bold interface featuring black, gold, and white themes, complemented by a pixel-art RoboGenie GIF.
- 🔁 **Replayable Experience**: Users can return and make different wishes to explore new fortunes each time.

---

## 📱 How It Works

1. **Launch the App**: You're greeted with a stylized layout featuring:
   - Title text
   - A pixelated RoboGenie GIF (credit to Sarah Burgess at https://maketodaycolourful.co.uk/)
   - A prompt asking you to "make a wish"
   - A button to progress through the experience

2. **Answer the Questions**:
   - "What is your favorite color?"
   - "Are you feeling lucky today?"
   - "What's your favorite season of the year?"
   - "What animal do you identify with the most?"

3. **Receive Your Fortune**:
   - RoboGenie responds with a mysterious and formatted message containing:
     - Your original wish
     - A uniquely crafted fortune
     - A decision: **Was your wish granted?**
   - Emojis are added to match the tone of the outcome.

4. **Restart or Replay**:
   - Tap a button to return to the home screen and begin again.

---

## 🧞‍♂️ AI Prompt Logic

The AI's behavior is guided by a custom prompt that asks it to:

- Speak like a wise, arcane fortune teller
- Format the response into three labeled sections:
  - `Wish:`
  - `Fortune:`
  - `Wish Granted?:`
- Use a maximum of **9 sentences**
- Include expressive emojis based on the nature of the outcome

The prompt dynamically incorporates the user's wish and question responses to create a compelling and customized fortune-telling experience.

---

## 🛠 Tech Stack

- **Language**: Kotlin
- **UI Layouts**: XML (`activity_main.xml`, `activity_fortune.xml`)
- **Backend**: OpenAI GPT-4 API (via Retrofit-style HTTP calls)
- **Architecture**: Simple stateful navigation between two activities
- **Media**: Animated RoboGenie GIF embedded in main view

---

## 🚀 Getting Started

To run RoboGenie locally:

- Download or clone the project files.
- Open the project in Android Studio.
- Add your OpenAI API key securely in the appropriate location within the code.
- Build and run the app on an Android emulator or physical device.

---

## 🔮 Possible Improvements

- Add more types of questions (e.g., trivia, personality-based)
- Incorporate voice input and text-to-speech responses
- Let users view or share past fortunes
- Add transitions, animations, or sound effects to enhance the mystical feel

---

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

🧞‍♂️ *“Speak your truth, reveal your essence… and let the RoboGenie whisper the future.”*

