package com.example.clockview.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.clockview.R
import com.example.clockview.classClockView.ClockView

class MainActivity : AppCompatActivity() {
    lateinit var clockview: ClockView
    lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        clockview = findViewById(R.id.ClockView)
        textView = findViewById(R.id.textView)

        textView.setOnClickListener {
            startActivity(Intent(this, ClockActivity::class.java))
        }

        clockview.apply {
            setOnClickListener {
            layoutParams.width += 100
            layoutParams.height += 100
                requestLayout()
        }}
    }
}