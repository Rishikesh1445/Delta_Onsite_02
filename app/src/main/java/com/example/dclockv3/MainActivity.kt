package com.example.dclockv3

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.example.dclockv3.ui.theme.DClockV3Theme
import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.core.app.NotificationCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }
        val alarmManager = AlarmManager(this)
        setContent {
            DClockV3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center){
                        Button(onClick = {
                            Intent(applicationContext, RunningService::class.java).also {
                                it.action=RunningService.Actions.START.toString()
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    startForegroundService(it)
                                } else {
                                    startService(it)
                                }
                            }
                        }) {
                            Text(text = "Start Service")
                        }
                        Button(onClick = {
                            Intent(applicationContext, RunningService::class.java).also {
                                it.action=RunningService.Actions.STOP.toString()
                                startService(it)
                            }
                        }) {
                            Text(text = "Stop Service")
                        }

                        Button(onClick = {
                            alarmManager.scheduleAlarm()
                        }) {
                            Text(text = "Set Alarm")
                        }
                    }
                }
            }
        }
    }

    private fun showNotification(text:String) {
        val notification = NotificationCompat.Builder(applicationContext, "channel_id")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Alarm!!!")
            .setContentText(text)
            .build()
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(3, notification)
    }
}