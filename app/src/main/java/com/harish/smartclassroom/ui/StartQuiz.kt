package com.harish.smartclassroom.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.harish.smartclassroom.R
import kotlinx.android.synthetic.main.activity_start_quiz.*

class StartQuiz : AppCompatActivity() {

    val totalQuestions =0
    val totalmarks = 0
    val duration by lazy {
        intent.getStringExtra("duration")
    }
    val examId by lazy { intent.getStringExtra("examId")}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_quiz)

        tv_questions_count.text = totalQuestions.toString()
        tv_exam_time .text = duration
        tv_exam_marks .text = totalmarks.toString()

    }

    fun onQuizStart(view: View) {
        val intent = Intent(this@StartQuiz,QuizActivity::class.java)
        intent.putExtra("examId",examId)
        intent.putExtra("duration",duration?.toInt())
        startActivity(intent)

    }
}