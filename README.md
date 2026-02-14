# keno-tracker
Track your Keno draws and get predictions with hot, cold, and trend-based number recommendations.

Build Instructions
To compile the APK, run:

bash
# Navigate to project directory
cd keno-tracker

# Build debug APK
./gradlew assembleDebug

# APK will be at: app/build/outputs/apk/debug/app-debug.apk
This complete app includes:

Input Screen - Interactive Keno board to log 20-number draws
Predict Screen - Hot, Cold, and Trend-based recommendations with confidence scores
Stats Screen - Overview statistics and draw history
Room Database - Persistent storage for all draws
MVVM + Clean Architecture - Proper separation of concerns
Hilt DI - Dependency injection
Jetpack Compose - Modern declarative UI
Material 3 - Dark casino-themed design

Keno Statistics Tracker - Log Draws & Predict Winners

Track your Keno game results and unlock the power of statistical analysis!

Features:

• Easy Data Entry - Tap the 20 numbers drawn on an interactive Keno board. It's fast, intuitive, and mirrors real Keno gameplay.

• Smart Predictions - Get recommendations based on three powerful algorithms:

Hot Numbers - The most frequently drawn numbers
Cold Numbers - The least drawn numbers (or never drawn)
Trend Numbers - Pattern-based predictions using recency weighting, gap analysis, and number clustering
• Detailed Statistics - View your total draws, average hits per draw, hit frequency distribution, and complete draw history.

• Pattern Analysis - Our trend algorithm considers recent frequency, time gaps, number clustering, and more to suggest numbers with mathematical edge.

How It Works:

Log each Keno round by tapping the 20 numbers the machine drew
The app builds a database of your results
View recommendations before your next game
Copy numbers directly to your Keno ticket
Whether you're a casual player or serious Keno enthusiast, Keno Statistics Tracker helps you make data-driven decisions.

Note: This app is for tracking and analysis purposes only. Keno is a game of chance with random outcomes. No prediction method can guarantee wins.

