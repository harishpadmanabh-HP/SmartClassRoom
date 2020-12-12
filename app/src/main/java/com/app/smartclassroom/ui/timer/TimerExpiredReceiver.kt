package com.app.smartclassroom.ui.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.app.smartclassroom.ui.QuizActivity
import com.app.smartclassroom.util.NotificationUtil
import com.app.smartclassroom.util.PrefUtil


class TimerExpiredReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        NotificationUtil.showTimerExpired(context)

        PrefUtil.setTimerState(QuizActivity.TimerState.Stopped, context)
        PrefUtil.setAlarmSetTime(0, context)
    }
}
