package com.example.dclockv3

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class AlarmService: Service() {

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                "running_channell",
                "Alarm",
                NotificationManager.IMPORTANCE_LOW
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("Rishi", "Alarm Recieved")

        notification("ALARMM")

        return START_STICKY
    }

    private fun notification(time:String){
        val notification = NotificationCompat.Builder(this, "running_channell")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentText(time)
            .build()
        startForeground(2,notification)
    }
}