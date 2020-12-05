package com.harish.smartclassroom.ui


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.florent37.viewtooltip.ViewTooltip
import com.harish.smartclassroom.R
import com.harish.smartclassroom.adapters.QuizAdapter
import com.harish.smartclassroom.adapters.QuizAnswers
import com.harish.smartclassroom.adapters.QuizListener
import com.harish.smartclassroom.data.AppData
import com.harish.smartclassroom.data.models.QuizResponse
import com.harish.smartclassroom.ui.timer.TimerExpiredReceiver
import com.harish.smartclassroom.util.NotificationUtil
import com.harish.smartclassroom.util.PrefUtil
import com.harish.smartclassroom.viewmodels.HomeViewModel
import com.harish.smartclassroom.viewmodels.HomeViewModelFactory
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.content_timer.*
import java.util.*

class QuizActivity : AppCompatActivity(),QuizListener {

    lateinit var viewModel : HomeViewModel
    lateinit var appData : AppData
    private val mAdapter by lazy { QuizAdapter(this) }
    var correctAnswered = 0
    val answeredQuestions:MutableList<Map<Int,QuizAnswers>> = mutableListOf()
    var isAnswered = false
    lateinit var quizTitle : String
    var wrongAnswered = 0
    var totalQuestions = 0

    private lateinit var timer: CountDownTimer
    private var timerLengthSeconds: Long = 0
    private var timerState = TimerState.Running

    private var secondsRemaining: Long = 0

    val examid by lazy { intent.getStringExtra("examId") }
    val duration by lazy { intent.getIntExtra("duration",0) }
    var examTitle = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val viewModelFactory = HomeViewModelFactory(application)
        viewModel = ViewModelProvider(this,viewModelFactory).get(HomeViewModel::class.java)
        appData = AppData.init(this)
        PrefUtil.setTimerLength(this,duration)
        setupObserver()
        viewModel.getQuiz(examid!!)
        //showOnboarding()
        initTimer()
        startTimer()
        timerState =  TimerState.Running

    }

    private fun setupObserver() {
        viewModel.apply {
            events.observe(this@QuizActivity, Observer {
                Toast.makeText(this@QuizActivity, "No data found", Toast.LENGTH_SHORT).show()
            })

            quiz.observe(this@QuizActivity, Observer {quizResponse->

                quizTitle = quizResponse.quizDetails[0].examTitle
                totalQuestions = (quizResponse.question.size)+1
                examTitle = quizResponse.quizDetails[0].examTitle
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
                              else
                                 wrongAnswered+=1
                       }
                   }
               }

            }

        }

        val intent = Intent(this@QuizActivity,QuizResult::class.java)
        intent.putExtra("total_Questions",totalQuestions)
        intent.putExtra("correct",correctAnswered)
        intent.putExtra("wrong",wrongAnswered)
        intent.putExtra("exam_name",quizTitle)

        startActivity(intent)



//        Toast.makeText(this, "Correct Answer = $correctAnswered", Toast.LENGTH_SHORT).show()


    }
    private fun onTimerFinished(){
        timerState = TimerState.Stopped

        //set the length of the timer to be the one set in SettingsActivity
        //if the length was changed when the timer was running
        setNewTimerLength()

        progress_countdown.progress = 0

        PrefUtil.setSecondsRemaining(timerLengthSeconds, this)
        secondsRemaining = timerLengthSeconds

        //updateButtons()
        updateCountdownUI()
    }

    private fun startTimer(){
        timerState = TimerState.Running

        timer = object : CountDownTimer(secondsRemaining * 1000, 1000) {
            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = millisUntilFinished / 1000
                Log.e("TICK","$secondsRemaining")
                updateCountdownUI()
            }
        }.start()
        timerState =  TimerState.Running

    }

    private fun setNewTimerLength(){
        val lengthInMinutes = PrefUtil.getTimerLength(this)
        timerLengthSeconds = (lengthInMinutes * 60L)
        progress_countdown.max = timerLengthSeconds.toInt()
    }

    private fun setPreviousTimerLength(){
        timerLengthSeconds = PrefUtil.getPreviousTimerLengthSeconds(this)
        progress_countdown.max = timerLengthSeconds.toInt()
    }

    private fun updateCountdownUI(){
        val minutesUntilFinished = secondsRemaining / 60
        val secondsInMinuteUntilFinished = secondsRemaining - minutesUntilFinished * 60
        val secondsStr = secondsInMinuteUntilFinished.toString()
        textView_countdown.text = "$minutesUntilFinished:${if (secondsStr.length == 2) secondsStr else "0" + secondsStr}"
        progress_countdown.progress = (timerLengthSeconds - secondsRemaining).toInt()

        Log.e("COUNTDOWN","$minutesUntilFinished:${if (secondsStr.length == 2) secondsStr else "0" + secondsStr}")
    }


    companion object {
        fun setAlarm(context: Context, nowSeconds: Long, secondsRemaining: Long): Long{
            val wakeUpTime = (nowSeconds + secondsRemaining) * 1000
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, TimerExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, wakeUpTime, pendingIntent)
            PrefUtil.setAlarmSetTime(nowSeconds, context)
            return wakeUpTime
        }

        fun removeAlarm(context: Context){
            val intent = Intent(context, TimerExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            PrefUtil.setAlarmSetTime(0, context)
        }

        val nowSeconds: Long
            get() = Calendar.getInstance().timeInMillis / 1000
    }

    enum class TimerState{
        Stopped, Paused, Running
    }
    override fun onResume() {
        super.onResume()

        initTimer()
        showTooltip()
        removeAlarm(this)
        NotificationUtil.hideTimerNotification(this)
    }

    override fun onPause() {
        super.onPause()

        if (timerState == TimerState.Running){
            timer.cancel()
            val wakeUpTime = setAlarm(this, nowSeconds, secondsRemaining)
            NotificationUtil.showTimerRunning(this, wakeUpTime)
        }
        else if (timerState == TimerState.Paused){
            NotificationUtil.showTimerPaused(this)
        }

        PrefUtil.setPreviousTimerLengthSeconds(timerLengthSeconds, this)
        PrefUtil.setSecondsRemaining(secondsRemaining, this)
        PrefUtil.setTimerState(timerState, this)
    }

    private fun initTimer(){
        timerState = PrefUtil.getTimerState(this)

        //we don't want to change the length of the timer which is already running
        //if the length was changed in settings while it was backgrounded
        if (timerState == TimerState.Stopped)
            setNewTimerLength()
        else
            setPreviousTimerLength()

        secondsRemaining = if (timerState == TimerState.Running || timerState == TimerState.Paused)
            PrefUtil.getSecondsRemaining(this)
        else
            timerLengthSeconds

        val alarmSetTime = PrefUtil.getAlarmSetTime(this)
        if (alarmSetTime > 0)
            secondsRemaining -= nowSeconds - alarmSetTime

        if (secondsRemaining <= 0)
            onTimerFinished()
        else if (timerState == TimerState.Running)
            startTimer()

        //updateButtons()
        updateCountdownUI()
    }

    fun showTooltip(){
     val tooltip=   ViewTooltip.on(fab_submit)
            .text("Click here to submit only after answering all questions.Swipe left or right to get previous and next question.")
            .position(ViewTooltip.Position.TOP)
            .color(R.color.black)
            .clickToHide(true)
            .autoHide(true,5000)
            .textColor(R.color.white)
            .corner(10)
            .arrowWidth(15)
            .arrowHeight(15)
            .show()
    }


}