package pl.pilichm.quizapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import pl.pilichm.quizapp.Constants
import pl.pilichm.quizapp.DatabaseHandler
import pl.pilichm.quizapp.UserScore
import pl.pilichm.quizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val userName = intent.getStringExtra(Constants.USER_NAME)
        val totalQuestions = intent.getStringExtra(Constants.TOTAL_QUESTIONS)
        val correctAnswers = intent.getStringExtra(Constants.CORRECT_ANSWERS)

        binding.tvName.text = userName
        binding.tvScore.text = "You're score is $correctAnswers out of $totalQuestions"

        binding.btnFinish.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.btnResultsToPodium.setOnClickListener {
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