package com.example.myapplication3.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Handler
import android.os.Looper
import android.util.Log

class WatchConnectionService : Service() {

    private val handler = Handler(Looper.getMainLooper())
    private val interval = 5000L // 5 seconds interval for data simulation

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "WatchConnectionService created")
        startSendingData()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "WatchConnectionService started")
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "WatchConnectionService destroyed")
        handler.removeCallbacksAndMessages(null)
    }

    private fun startSendingData() {
        handler.post(object : Runnable {
            override fun run() {
                // Simulate heart rate and SpO2 values
                val heartRate = (60..120).random()
                val spO2 = (90..100).random()

                // Send data to the mobile app
                val intent = Intent("com.example.myapplication.HEART_DATA")
                intent.putExtra("heartRate", heartRate)
                intent.putExtra("spO2", spO2)
                sendBroadcast(intent)

                Log.d(TAG, "Sent heartRate: $heartRate, spO2: $spO2")

                // Continue to send data every 5 seconds
                handler.postDelayed(this, interval)
            }
        })
    }

    companion object {
        private const val TAG = "WatchConnectionService"
    }
}
