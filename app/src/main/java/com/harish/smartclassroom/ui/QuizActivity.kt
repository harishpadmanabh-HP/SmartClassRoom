package com.harish.smartclassroom.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.harish.smartclassroom.R
import com.harish.smartclassroom.adapters.QuizAdapter
import com.harish.smartclassroom.adapters.QuizAnswers
import com.harish.smartclassroom.adapters.QuizListener
import com.harish.smartclassroom.data.AppData
import com.harish.smartclassroom.data.models.QuizResponse
import com.harish.smartclassroom.viewmodels.HomeViewModel
import com.harish.smartclassroom.viewmodels.HomeViewModelFactory
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity(),QuizListener {

    lateinit var viewModel : HomeViewModel
    lateinit var appData : AppData
    private val mAdapter by lazy { QuizAdapter(this) }
    var correctAnswered = 0
    val answeredQuestions:MutableList<Map<Int,QuizAnswers>> = mutableListOf()
    var isAnswered = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val viewModelFactory = HomeViewModelFactory(application)
        viewModel = ViewModelProvider(this,viewModelFactory).get(HomeViewModel::class.java)
        appData = AppData.init(this)
        setupObserver()
        viewModel.getQuiz("1")
        //showOnboarding()
    }

    private fun setupObserver() {
        viewModel.apply {
            events.observe(this@QuizActivity, Observer {
                Toast.makeText(this@QuizActivity, "No data found", Toast.LENGTH_SHORT).show()
            })

            quiz.observe(this@QuizActivity, Observer {quizResponse->

                if(!quizResponse?.question.isNullOrEmpty()){
                    mAdapter.setQuizList(quizResponse.question)
                    vp_quizCard.adapter = mAdapter

                }

            })

        }

    }

    override fun onQuizSwipe(position: Int) {
        tv_qno . text =(position+1).toString()
        Log.e("swipe position",position.toString())
    }

    override fun onQuizAnswered(
        currentQuiz: QuizResponse.Question,
        position: Int,
        answered: QuizAnswers
    ) {

        val map : Map<Int,QuizAnswers> = mapOf(position to answered)

        if(!answeredQuestions.isNullOrEmpty()){
            handlePreviouslyAnswered(answeredQuestions,position)
        }

        answeredQuestions.add(map)
        Log.e("maps",answeredQuestions.toString())



    }

    private fun handlePreviouslyAnswered(answeredQuestions: MutableList<Map<Int, QuizAnswers>>, position: Int) {
        val iterator =answeredQuestions.iterator()
        while (iterator.hasNext()){
            val item = iterator.next()
            if(item.containsKey(position))
                iterator.remove()
        }
    }


    private fun compareAnswer(correctAnswer: String, answered: QuizAnswers):Boolean {

        var answerStatus =false
        var answerdOption = "nothing"

        when (answered){
            QuizAnswers.A -> answerdOption="a"
            QuizAnswers.B -> answerdOption="b"
            QuizAnswers.C -> answerdOption="c"
            QuizAnswers.D -> answerdOption="d"
        }

        if(correctAnswer.equals(answerdOption))
            answerStatus =true

        return answerStatus


    }


    override fun onQuizShown(position: Int, currentQuiz: QuizResponse.Question) {

        //isAnswered = currentQuiz.isAnswered
        Log.e("currentPosition",position.toString())

    }

    fun onQuizFinished(view: View) {

        val quizList = viewModel.quiz.value
        quizList?. let{ quizResponse ->
            quizResponse.question.forEachIndexed { index, question ->
               val correctAnswer = question.correctAnswer
               if(!answeredQuestions.isNullOrEmpty()){
                   answeredQuestions.forEach {
                       if(it.contains(index)) {
                           val attemptedAnswer = it.getValue(index)
                             if(compareAnswer(correctAnswer,attemptedAnswer))
                                 correctAnswered+=1
                       }
                   }
               }

            }

        }

        Toast.makeText(this, "Correct Answer = $correctAnswered", Toast.LENGTH_SHORT).show()


    }

}