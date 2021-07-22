package pl.pilichm.quizapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class UserScoreAdapter(context: Context, userScores: ArrayList<UserScore>):
    ArrayAdapter<UserScore>(context, 0, userScores) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val currentUserScore = getItem(position)
        val currentView = convertView ?: LayoutInflater.from(context).inflate(R.layout.leader_board_element, parent, false);

        val tvOrderNumber = convertView?.findViewById<TextView>(R.id.tv_order_number)
        val tvUserName = convertView?.findViewById<TextView>(R.id.tv_user_name)
        val tvUserScore = convertView?.findViewById<TextView>(R.id.tv_user_score)

        tvOrderNumber?.text = currentUserScore?.scoreNumber.toString()
        tvUserName?.text = currentUserScore?.userName
        tvUserScore?.text = "${currentUserScore?.score.toString()}/10"

        return currentView
    }
}