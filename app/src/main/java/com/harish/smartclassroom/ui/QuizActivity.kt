package com.harish.smartclassroom.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.harish.smartclassroom.R
import com.harish.smartclassroom.adapters.AssignmentAdapter
import com.harish.smartclassroom.adapters.QuizAdapter
import com.harish.smartclassroom.adapters.QuizListener
import com.harish.smartclassroom.data.AppData
import com.harish.smartclassroom.viewmodels.HomeViewModel
import com.harish.smartclassroom.viewmodels.HomeViewModelFactory
import com.harish.smartclassroom.viewmodels.OnBoardingViewModel
import com.harish.smartclassroom.viewmodels.OnbaordingViewModelFactory
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity(),QuizListener {
    lateinit var viewModel : HomeViewModel
    lateinit var appData : AppData
    private val mAdapter by lazy { QuizAdapter(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val viewModelFactory = HomeViewModelFactory(application)
        viewModel = ViewModelProvider(this,viewModelFactory).get(HomeViewModel::class.java)
        appData = AppData.init(this)
        setupObserver()
        viewModel.getQuiz("1")
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
    }
}