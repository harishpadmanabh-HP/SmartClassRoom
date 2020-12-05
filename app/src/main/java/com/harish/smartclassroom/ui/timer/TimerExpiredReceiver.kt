package com.harish.smartclassroom.ui.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.harish.smartclassroom.ui.QuizActivity
import com.harish.smartclassroom.util.NotificationUtil
import com.harish.smartclassroom.util.PrefUtil


class TimerExpiredReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        NotificationUtil.showTimerExpired(context)

        PrefUtil.setTimerState(QuizActivity.TimerState.Stopped, context)
        PrefUtil.setAlarmSetTime(0, context)
    }
}
