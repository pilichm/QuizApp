package pl.pilichm.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition = 0
    private var mCorrectAnswers: Int = 0
    private var mUserName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        mQuestionsList = Constants.getQuestions()
        setQuestion()
    }

    private fun setQuestion(){
        val questionsList = Constants.getQuestions()
        val progressBar = findViewById<ProgressBar>(R.id.pb_progress_bar)
        val progressText = findViewById<TextView>(R.id.tv_progress)
        val tvQuestion = findViewById<TextView>(R.id.tv_question_id)
        val ivImage = findViewById<ImageView>(R.id.iv_image)
        val tvAnswerOne = findViewById<TextView>(R.id.tv_option_one)
        val tvAnswerTwo = findViewById<TextView>(R.id.tv_option_two)
        val tvAnswerThree = findViewById<TextView>(R.id.tv_option_three)
        val tvAnswerFour = findViewById<TextView>(R.id.tv_option_four)
        val btnSubmit = findViewById<Button>(R.id.btn_submit)

        tvAnswerOne.setOnClickListener(this)
        tvAnswerTwo.setOnClickListener(this)
        tvAnswerThree.setOnClickListener(this)
        tvAnswerFour.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)

        val question = mQuestionsList!![mCurrentPosition-1]

        defaultOptionsView()

        if (mCurrentPosition == questionsList!!.size){
            btnSubmit.text = "FINISH"
        } else{
            btnSubmit.text = "SUBMIT"
        }

        progressBar.progress = mCurrentPosition
        progressText.text = "$mCurrentPosition/${questionsList.size}"
        tvQuestion.text = question.question
        ivImage.setImageResource(question.image)
        tvAnswerOne.text = question.optionOne
        tvAnswerTwo.text = question.optionTwo
        tvAnswerThree.text = question.optionThree
        tvAnswerFour.text = question.optionFour
    }

    private fun defaultOptionsView(){
        val tvAnswerOne = findViewById<TextView>(R.id.tv_option_one)
        val tvAnswerTwo = findViewById<TextView>(R.id.tv_option_two)
        val tvAnswerThree = findViewById<TextView>(R.id.tv_option_three)
        val tvAnswerFour = findViewById<TextView>(R.id.tv_option_four)
        val options = java.util.ArrayList<TextView>()
        options.add(0, tvAnswerOne)
        options.add(1, tvAnswerTwo)
        options.add(2, tvAnswerThree)
        options.add(3, tvAnswerFour)

        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this, R.drawable.default_option_border_bg)
        }

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_option_one -> {
                selectedOptionView(v as TextView, 1)
            }
            R.id.tv_option_two -> {
                selectedOptionView(v as TextView, 2)
            }
            R.id.tv_option_three -> {
                selectedOptionView(v as TextView, 3)
            }
            R.id.tv_option_four -> {
                selectedOptionView(v as TextView, 4)
            }
            R.id.btn_submit -> {
                if (mSelectedOptionPosition==0){
                    mCurrentPosition++

                    when {
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        } else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size.toString())
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers.toString())
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    val question = mQuestionsList?.get(mCurrentPosition-1)
                    if (question!!.correctAnswer!=mSelectedOptionPosition){
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border)
                    } else {
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border)

                    if (mCurrentPosition == mQuestionsList!!    .size){
                        (v as Button).text = "FINISH"
                    } else {
                        (v as Button).text = "GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPosition = 0

                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int){
        val tvAnswerOne = findViewById<TextView>(R.id.tv_option_one)
        val tvAnswerTwo = findViewById<TextView>(R.id.tv_option_two)
        val tvAnswerThree = findViewById<TextView>(R.id.tv_option_three)
        val tvAnswerFour = findViewById<TextView>(R.id.tv_option_four)
        when (answer){
            1 -> tvAnswerOne.background = ContextCompat.getDrawable(this, drawableView)
            2 -> tvAnswerTwo.background = ContextCompat.getDrawable(this, drawableView)
            3 -> tvAnswerThree.background = ContextCompat.getDrawable(this, drawableView)
            4 -> tvAnswerFour.background = ContextCompat.getDrawable(this, drawableView)
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNumber: Int){
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNumber
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this, R.drawable.selected_option_border)
    }


}