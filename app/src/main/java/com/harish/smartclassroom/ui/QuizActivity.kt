package com.harish.smartclassroom.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
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
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig

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

        //val isAnswered = currentQuiz.isAnswered
        Log.e("is Answeere",isAnswered.toString())
        // store answer choices with pos and selection
        var hashMap : HashMap<Int, QuizAnswers>
        = HashMap<Int, QuizAnswers> ()
        if(isAnswered)
        hashMap.put(position,answered)

        //add all hashmap with <POS,SELECTION> to list
        answeredQuestions.add(hashMap)

        answeredQuestions.forEach {
            if(it.containsKey(position)){//already attempted
                //check if already answered question is correct
                val previouslyAnswered = it.getValue(position)
                  if(compareAnswer(currentQuiz,previouslyAnswered) && !compareAnswer(currentQuiz,answered)){
                      correctAnswered-=1

                  }else if(!compareAnswer(currentQuiz,previouslyAnswered) && compareAnswer(currentQuiz,answered)){
                      correctAnswered+=1
                  }
            }else{
                //answering tht question for first time
                if(compareAnswer(currentQuiz,answered))
                    correctAnswered += 1
            }
        }
        Log.e("Correct answer",correctAnswered.toString())



//        if(answeredQuestions.con(position)){//already answered.changed answer
//
//        }else{// answering for first time
//            if(compareAnswer(currentQuiz,answered))
//                correctAnswered += 1
//
//            Log.e("Correct answer",correctAnswered.toString())
//
//        }







    }

    private fun compareAnswer(currentQuiz: QuizResponse.Question, answered: QuizAnswers):Boolean {

        var answerStatus =false
        var answerdOption = "nothing"

        when (answered){
            QuizAnswers.A -> answerdOption="a"
            QuizAnswers.B -> answerdOption="b"
            QuizAnswers.C -> answerdOption="c"
            QuizAnswers.D -> answerdOption="d"
        }

        if(currentQuiz.correctAnswer.equals(answerdOption))
            answerStatus =true

        return answerStatus


    }


    override fun onQuizShown(position: Int, currentQuiz: QuizResponse.Question) {

        isAnswered = currentQuiz.isAnswered

    }

}