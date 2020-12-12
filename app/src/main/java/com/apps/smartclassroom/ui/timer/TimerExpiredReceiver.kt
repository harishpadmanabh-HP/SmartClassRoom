package com.apps.smartclassroom.ui.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.apps.smartclassroom.ui.QuizActivity
import com.apps.smartclassroom.util.NotificationUtil
import com.apps.smartclassroom.util.PrefUtil


class TimerExpiredReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        NotificationUtil.showTimerExpired(context)

        PrefUtil.setTimerState(QuizActivity.TimerState.Stopped, context)
        PrefUtil.setAlarmSetTime(0, context)
    }
}
