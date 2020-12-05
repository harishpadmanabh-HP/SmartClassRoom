package com.harish.smartclassroom.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.futured.donut.DonutSection
import com.harish.smartclassroom.R
import kotlinx.android.synthetic.main.activity_quiz_result.*

class QuizResult : AppCompatActivity() {

    val correctAnswers by lazy {
        intent.getIntExtra("correct",0)
    }

    val wrongAnswers by lazy { intent.getIntExtra("wrong",0) }
    val totalQuestions by lazy{ intent.getIntExtra("total_Questions",0)}
    val examName by lazy { intent.getStringExtra("exam_name") }
    var notAnswered = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)

        exam .text = examName


        notAnswered = totalQuestions - (correctAnswers+wrongAnswers)

        setupChart()

        }

    private fun setupChart() {
        val section1 = DonutSection(
            name = "Correct",
            color = Color.parseColor("#95C730"),
            amount = correctAnswers.toFloat()
        )

        val section2 = DonutSection(
            name = "Wrong",
            color = Color.parseColor("#FA1807"),
            amount = wrongAnswers.toFloat()
        )

        val section3 = DonutSection(
            name = "Not Answered",
            color = Color.parseColor("#6C5DD3"),
            amount = notAnswered.toFloat()
        )

        donut_view.cap = totalQuestions.toFloat()
        donut_view.animationDurationMs = 3000

        donut_view.submitData(listOf(section1, section2,section3))

        tv_correct.text = correctAnswers.toString()
        tv_not_answered.text = notAnswered.toString()
        tv_wrong.text=wrongAnswers.toString()
    }

    override fun onBackPressed() {
        startActivity(Intent(this@QuizResult,StudentHome::class.java))
    }
}