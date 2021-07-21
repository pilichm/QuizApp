package pl.pilichm.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_leader_board.*

class LeaderBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)

        btn_return_to_start.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val mockData = ArrayList<UserScore>()
        mockData.add(UserScore(1, "pilichm pilichm", 1))
        mockData.add(UserScore(2, "alaalajaja", 4))
        mockData.add(UserScore(3, "dodoctorctor", 5))
        mockData.add(UserScore(4, "doctordoctordoctor", 7))

        val adapter = UserScoreAdapter(this, mockData)
        lv_user_scores.adapter = adapter
    }
}