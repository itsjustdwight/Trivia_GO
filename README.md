# TRIVIA GO APP

Project for CSC 436

Authors: Sanaia Pierre & Dwight Taylor Jr.


Trivia GO! is a simple trivia game that tests your knowledge in 7 different categories: History, 
Geography, Science and Math, Pop Culture, Sports & Games, Literature, and Mixed Knowledge 
(combination of the other 6 categories). To test this knowledge, you are tested with 4 different
question types. Answers can be submitted either through Multiple Choice, True or False, Matching, or 
Reordering. Each category has 4 questions (each a different type) that you will have 10 seconds to 
answer. If you don't answer within that time, the question will be marked as incomplete. At the end of 
a category, you will be able to see how many questions you got right. Hope you enjoy!

Figma Link: 
https://www.figma.com/design/IZeaepO0UcUnxLgRsyncTn/MobileAppDev?node-id=0-1&m=dev&t=hd4KIKOd5l2Wp5ko-1

Android and Jetpack Compose Features"
- used a Sealed class to hold our routes and handle screen transitions; clearly defined the categories as a sub-hierarchical level below the Opening and Home screens
- imported from Material Design 3 for the look of our app
- used a ViewModel to hold state update information (e.g., timer, score, and question transition)
- used a Room Database to store the game categories and the questions/answers we would use for our game
- used built-in Kotlin shuffle logic to randomly choose a question/answer type so they don't appear in the same order every time

Mobile Platform Capabilities We Used
- touch interfaces and gestures; used for Matching and Rearranging answers because the player can drag the answers to be in a certain spot, which gets checked for correctness
- graphics; used graphics and icons to visually represent our different game categories
