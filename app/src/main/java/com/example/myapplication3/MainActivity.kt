package com.example.myapplication3

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var heartRateTextView: TextView
    private lateinit var spO2TextView: TextView

    private val heartRateThreshold = 100
    private val spO2Threshold = 95

    private val heartDataReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val heartRate = intent.getIntExtra("heartRate", 0)
            val spO2 = intent.getIntExtra("spO2", 0)

            heartRateTextView.text = "Heart Rate: $heartRate"
            spO2TextView.text = "SpO2: $spO2"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize TextViews
        heartRateTextView = findViewById(R.id.heartRateTextView)
        spO2TextView = findViewById(R.id.spO2TextView)

        // Register the BroadcastReceiver to listen for heart rate and SpO2 data with RECEIVER_NOT_EXPORTED
        val intentFilter = IntentFilter("com.example.myapplication3.HEART_DATA")
        registerReceiver(heartDataReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED)
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(heartDataReceiver)
    }
}
