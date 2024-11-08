package com.example.dclockv3

import android.content.Context
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.SystemClock

class AlarmManager(
    private val context: Context
) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun scheduleAlarm(){
        val fiveSeconds = 1000*5

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            Intent(context, AlarmReciever::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            SystemClock.elapsedRealtime() + fiveSeconds,
            pendingIntent
        )
    }

    fun cancelAlarm(){
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            Intent(context, AlarmReciever::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)
    }
}