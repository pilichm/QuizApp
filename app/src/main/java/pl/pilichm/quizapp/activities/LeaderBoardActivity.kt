package pl.pilichm.quizapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.pilichm.quizapp.DatabaseHandler
import pl.pilichm.quizapp.UserScoreAdapter
import pl.pilichm.quizapp.databinding.ActivityLeaderBoardBinding

class LeaderBoardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLeaderBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReturnToStart.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val dbHandler = DatabaseHandler.getDatabaseHandler(this)

        val adapter = UserScoreAdapter(this, dbHandler.getTopScores())
        binding.lvUserScores.adapter = adapter
    }
}