package dev.csse.dtaylo37.triviago.ui.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TriviaRepository(context: Context) {

    private val databaseCallback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            CoroutineScope(Dispatchers.IO).launch {
                starterData()
            }
        }
    }

    private val database: TriviaDatabase = Room.databaseBuilder(
        context,
        TriviaDatabase::class.java,
        "trivia.db"
    )
        .fallbackToDestructiveMigration()
        .addCallback(databaseCallback)
        .build()

    private val gameCategoryDao = database.GameCategoryDao()
    private val questionDao = database.QuestionDao()

    fun getGameCategory(gameCategoryId: Long) = gameCategoryDao.getGameCategory(gameCategoryId)
    fun getGameCategories() = gameCategoryDao.getGameCategories()

    fun getQuestion(questionId: Long) = questionDao.getQuestion(questionId)
    fun getQuestions(gameCategoryId: Long) = questionDao.getQuestions(gameCategoryId)

    fun addQuestion(question: Question) {
        CoroutineScope(Dispatchers.IO).launch {
            question.id = questionDao.addQuestion(question)
        }
    }

    fun updateQuestion(question: Question) {
        CoroutineScope(Dispatchers.IO).launch {
            questionDao.updateQuestion(question)
        }
    }

    fun deleteQuestion(question: Question) {
        CoroutineScope(Dispatchers.IO).launch {
            questionDao.deleteQuestion(question)
        }
    }

    private suspend fun starterData() {
        val categories = listOf("History", "Geography", "Science & Math", "Pop Culture", "Sports & Games", "Literature", "Mixed Knowledge")
        
        categories.forEach { categoryName ->
            val catId = gameCategoryDao.addGameCategory(GameCategory(categoryName = categoryName))

            when (categoryName) {
                "History" -> {
                    addMC(
                        catId,
                        "Who was the first president of the United States?",
                        listOf(
                            "George Washington",
                            "Thomas Jefferson",
                            "Abraham Lincoln",
                            "John Adams"
                        ),
                        "George Washington"
                    )
                    addTF(catId, "True or False: The Magna Carta was signed in 1215.", "True")
                    addMatch(
                        catId,
                        "Match the leader to their country:",
                        listOf(
                            "Churchill", "UK",
                            "Roosevelt", "USA",
                            "Stalin", "USSR",
                            "Napoleon", "France"
                        )
                    )
                    addRearrange(
                        catId,
                        "Order these events from earliest to latest:",
                        listOf("American Revolution", "Civil War", "WWI", "WWII"),
                        "American Revolution,Civil War,WWI,WWII"
                    )
                }

                "Geography" -> {
                    addMC(
                        catId,
                        "What is the capital of Japan?",
                        listOf("Tokyo", "Seoul", "Beijing", "Bangkok"),
                        "Tokyo"
                    )
                    addTF(
                        catId,
                        "True or False: The Nile is the longest river in the world.",
                        "True"
                    )
                    addMatch(
                        catId,
                        "Match the city to its country:",
                        listOf("Cairo", "Egypt", "Lima", "Peru", "Oslo", "Norway", "Tokyo", "Japan")
                    )
                    addRearrange(
                        catId,
                        "Order these continents by area (Largest to Smallest):",
                        listOf("Asia", "Africa", "North America", "Europe"),
                        "Asia,Africa,North America,Europe"
                    )
                }

                "Science & Math" -> {
                    addMC(
                        catId,
                        "What is the chemical symbol for Gold?",
                        listOf("Au", "Ag", "Fe", "Pb"),
                        "Au"
                    )
                    addTF(catId, "True or False: Light travels faster than sound.", "True")
                    addMatch(
                        catId,
                        "Match the element to its symbol:",
                        listOf("Iron", "Fe", "Lead", "Pb", "Tin", "Sn", "Mercury", "Hg")
                    )
                    addRearrange(
                        catId,
                        "Order the planets by distance from Sun (Closest first):",
                        listOf("Mercury", "Venus", "Earth", "Mars"),
                        "Mercury,Venus,Earth,Mars"
                    )
                }

                "Pop Culture" -> {
                    addMC(
                        catId,
                        "Which franchise hasn't Mark Hamill been a part of?",
                        listOf("Star Wars", "Batman", "Indiana Jones", "Avatar"),
                        "Indiana Jones"
                    )
                    addTF(
                        catId,
                        "True or False: Michael Jackson won 8 Grammys in a single night.",
                        "True"
                    )
                    addMatch(
                        catId,
                        "Match the actor's first and last names:",
                        listOf(
                            "Harrison", "Ford",
                            "Lionel", "Messi",
                            "Mark", "Hamill",
                            "Tom", "Cruise"
                        )
                    )
                    addRearrange(
                        catId,
                        "Order these movies by release date (Earliest first):",
                        listOf("Iron Man", "The Avengers", "Black Panther", "Endgame"),
                        "Iron Man,The Avengers,Black Panther,Endgame"
                    )
                }

                "Sports & Games" -> {
                    addMC(
                        catId,
                        "How many players are on a basketball court per team?",
                        listOf("4", "5", "6", "7"),
                        "5"
                    )
                    addTF(catId, "True or False: A touchdown is worth 6 points.", "True")
                    addMatch(
                        catId,
                        "Match the athlete to their sport:",
                        listOf(
                            "Tiger Woods", "Golf",
                            "LeBron James", "Basketball",
                            "Lionel Messi", "Soccer",
                            "Serena Williams", "Tennis"
                        )
                    )
                    addRearrange(
                        catId,
                        "Order these sports by ball size (Smallest to Largest):",
                        listOf("Golf", "Baseball", "Soccer", "Basketball"),
                        "Golf,Baseball,Soccer,Basketball"
                    )
                }

                "Literature" -> {
                    addMC(
                        catId,
                        "Who wrote '1984'?",
                        listOf("George Orwell", "Aldous Huxley", "Ray Bradbury", "J.K. Rowling"),
                        "George Orwell"
                    )
                    addTF(
                        catId,
                        "True or False: Sherlock Holmes lived at 221B Baker Street.",
                        "True"
                    )
                    addMatch(
                        catId,
                        "Match the author to their famous work:",
                        listOf(
                            "F. Scott Fitzgerald", "The Great Gatsby",
                            "Herman Melville", "Moby Dick",
                            "Mary Shelley", "Frankenstein",
                            "Jane Austen", "Emma"
                        )
                    )
                    addRearrange(
                        catId,
                        "Order these plays by Shakespeare (Earliest first):",
                        listOf("Romeo and Juliet", "Hamlet", "Othello", "The Tempest"),
                        "Romeo and Juliet,Hamlet,Othello,The Tempest"
                    )
                }

                "Mixed Knowledge" -> {
                    addMC(
                        catId,
                        "What is the capital of France?",
                        listOf("Paris", "London", "Berlin", "Rome"),
                        "Paris"
                    )
                    addTF(catId, "True or False: The human body has four lungs.", "False")
                    addMatch(
                        catId,
                        "Match the currency to the country:",
                        listOf("Yen", "Japan", "Euro", "Germany", "Peso", "Mexico", "Dollar", "USA")
                    )
                    addRearrange(
                        catId,
                        "Order these units of measurement (Smallest to Largest):",
                        listOf("Millimeter", "Centimeter", "Meter", "Kilometer"),
                        "Millimeter,Centimeter,Meter,Kilometer"
                    )
                }
            }
        }
    }

    // Helper functions to keep starterData clean
    private suspend fun addMC(catId: Long, text: String, opts: List<String>, correct: String) {
        questionDao.addQuestion(
            Question(
                text = text, 
                answerOptions = opts, 
                correctAnswer = correct, 
                categoryId = catId, 
                type = QuestionType.MC)
        )
    }
    private suspend fun addTF(catId: Long, text: String, correct: String) {
        questionDao.addQuestion(
            Question(
                text = text, 
                answerOptions = listOf("True", "False"), 
                correctAnswer = correct, 
                categoryId = catId, 
                type = QuestionType.TF)
        )
    }
    private suspend fun addMatch(catId: Long, text: String, pairs: List<String>) {
        val correctOrderString = pairs.filterIndexed { i, _ -> i % 2 != 0 }.joinToString(",")
        questionDao.addQuestion(
            Question(
                text = text, 
                answerOptions = pairs, 
                correctAnswer = correctOrderString,
                categoryId = catId, 
                type = QuestionType.MATCH)
        )
    }
    private suspend fun addRearrange(catId: Long, text: String, items: List<String>, correctOrder: String) {
        questionDao.addQuestion(
            Question(
                text = text, 
                answerOptions = items, 
                correctAnswer = correctOrder, 
                categoryId = catId,
                type = QuestionType.REARRANGE))
    }
}
