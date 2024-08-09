package com.example.dclockv3

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RunningService: Service() {

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                "running_channel",
                "Current Time",
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
        when(intent?.action){
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
        return START_STICKY
    }

    @SuppressLint("ForegroundServiceType")
    private fun start(){
        CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                notification("Current time : ${ timeFormat.format(Date()) }")
                delay(1000)
            }
        }
    }

    private fun notification(time:String){
        val notification = NotificationCompat.Builder(this, "running_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentText(time)
            .build()
        startForeground(1,notification)
    }

    enum class Actions {
        START, STOP
    }
}