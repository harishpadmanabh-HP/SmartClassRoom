package com.app.smartclassroom.ui.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.app.smartclassroom.ui.QuizActivity
import com.app.smartclassroom.util.NotificationUtil
import com.app.smartclassroom.util.PrefUtil


class TimerNotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action){
            AppConstants.ACTION_STOP -> {
                QuizActivity.removeAlarm(context)
                PrefUtil.setTimerState(QuizActivity.TimerState.Stopped, context)
                NotificationUtil.hideTimerNotification(context)
            }
            AppConstants.ACTION_PAUSE -> {
                var secondsRemaining = PrefUtil.getSecondsRemaining(context)
                val alarmSetTime = PrefUtil.getAlarmSetTime(context)
                val nowSeconds = QuizActivity.nowSeconds

                secondsRemaining -= nowSeconds - alarmSetTime
                PrefUtil.setSecondsRemaining(secondsRemaining, context)

                QuizActivity.removeAlarm(context)
                PrefUtil.setTimerState(QuizActivity.TimerState.Paused, context)
                NotificationUtil.showTimerPaused(context)
            }
            AppConstants.ACTION_RESUME -> {
                val secondsRemaining = PrefUtil.getSecondsRemaining(context)
                val wakeUpTime = QuizActivity.setAlarm(context, QuizActivity.nowSeconds, secondsRemaining)
                PrefUtil.setTimerState(QuizActivity.TimerState.Running, context)
                NotificationUtil.showTimerRunning(context, wakeUpTime)
            }
            AppConstants.ACTION_START -> {
                val minutesRemaining = PrefUtil.getTimerLength(context)
                val secondsRemaining = minutesRemaining * 60L
                val wakeUpTime = QuizActivity.setAlarm(context, QuizActivity.nowSeconds, secondsRemaining)
                PrefUtil.setTimerState(QuizActivity.TimerState.Running, context)
                PrefUtil.setSecondsRemaining(secondsRemaining, context)
                NotificationUtil.showTimerRunning(context, wakeUpTime)
            }
        }
    }
}
