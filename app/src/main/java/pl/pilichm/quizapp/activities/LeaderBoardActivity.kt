package pl.pilichm.quizapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_leader_board.*
import pl.pilichm.quizapp.DatabaseHandler
import pl.pilichm.quizapp.R
import pl.pilichm.quizapp.UserScoreAdapter

class LeaderBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)

        btn_return_to_start.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val dbHandler = DatabaseHandler.getDatabaseHandler(this)

        val adapter = UserScoreAdapter(this, dbHandler.getTopScores())
        lv_user_scores.adapter = adapter
    }
}