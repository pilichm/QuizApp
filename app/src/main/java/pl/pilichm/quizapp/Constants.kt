package pl.pilichm.quizapp

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

object Constants{
    const val USER_NAME: String = "user_name"
    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS: String = "correct_answers"

    /**
     * CSV file headers.
     */
    private const val COL_QUESTION_ID = 0
    private const val COL_QUESTION_TEXT = 1
    private const val COL_CORRECT_COUNTRY_NAME = 6
    private const val COL_OPTION_1 = 2
    private const val COL_OPTION_2 = 3
    private const val COL_OPTION_3 = 4
    private const val COL_OPTION_4 = 5
    private const val COL_CORRECT_ANSWER_NUM = 7
    private const val DELIMITER = ","
    const val CSV_FILENAME = "questions.csv"

    private fun getFlagByCountryName(countryName: String) = when(countryName){
        "Argentina" -> R.drawable.ic_flag_of_argentina
        "Australia" -> R.drawable.ic_flag_of_australia
        "Brazil" -> R.drawable.ic_flag_of_brazil
        "Belgium" -> R.drawable.ic_flag_of_belgium
        "Fiji" -> R.drawable.ic_flag_of_fiji
        "Germany" -> R.drawable.ic_flag_of_germany
        "Denmark" -> R.drawable.ic_flag_of_denmark
        "India" -> R.drawable.ic_flag_of_india
        "New Zealand" -> R.drawable.ic_flag_of_new_zealand
        "Kuwait" -> R.drawable.ic_flag_of_kuwait
        else -> R.drawable.ic_flag_of_brazil
    }

  fun getQuestions(open: InputStream): ArrayList<Question>{
      val questionsList = ArrayList<Question>()
      var line: String?

      val streamReader = InputStreamReader(open)
      val reader = BufferedReader(streamReader)

      // Skip header.
      reader.readLine()

      line = reader.readLine()
      while (line != null){
          val tokens = line.split(DELIMITER)
          if (tokens.isNotEmpty()){
              questionsList.add(
                  Question(
                      Integer.parseInt(tokens[COL_QUESTION_ID]),
                      tokens[COL_QUESTION_TEXT],
                      getFlagByCountryName(tokens[COL_CORRECT_COUNTRY_NAME]),
                      tokens[COL_OPTION_1],
                      tokens[COL_OPTION_2],
                      tokens[COL_OPTION_3],
                      tokens[COL_OPTION_4],
                      Integer.parseInt(tokens[COL_CORRECT_ANSWER_NUM])
                  )
              )
          }

          line = reader.readLine()
      }

      return questionsList
  }
}