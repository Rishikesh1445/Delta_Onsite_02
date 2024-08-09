package com.example.dclockv3

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat

class AlarmReciever : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("Rishi", "Alarm Recievedd")

        val notification = context?.let {
            NotificationCompat.Builder(it, "running_channell")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText("Alarm")
                .build()
        }
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(2, notification)
    }
}