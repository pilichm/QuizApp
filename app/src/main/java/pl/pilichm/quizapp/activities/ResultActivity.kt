package pl.pilichm.quizapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_result.*
import pl.pilichm.quizapp.Constants
import pl.pilichm.quizapp.DatabaseHandler
import pl.pilichm.quizapp.R
import pl.pilichm.quizapp.UserScore

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val userName = intent.getStringExtra(Constants.USER_NAME)
        val totalQuestions = intent.getStringExtra(Constants.TOTAL_QUESTIONS)
        val correctAnswers = intent.getStringExtra(Constants.CORRECT_ANSWERS)

        tv_name.text = userName
        tv_score.text = "You're score is $correctAnswers out of $totalQuestions"

        btn_finish.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        btn_results_to_podium.setOnClickListener {
            val intent = Intent(this, LeaderBoardActivity::class.java)
            startActivity(intent)
            finish()
        }

        val dbHandler = DatabaseHandler.getDatabaseHandler(this)

        if (userName!=null&&correctAnswers!=null){
            dbHandler.saveResult(UserScore(0, userName, correctAnswers.toInt()))
        }
    }
}