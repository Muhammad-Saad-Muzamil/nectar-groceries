package com.example.nectar

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OrderSuccessActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_success)

        // Buttons
        val trackOrderButton = findViewById<Button>(R.id.btnTrackOrder)
        val backToHomeButton = findViewById<TextView>(R.id.btnBackToHome)

        // Handle "Track Order" button click (You can implement tracking logic later)
        trackOrderButton.setOnClickListener {
            // Open a Tracking Activity (Create later if needed)
            // val intent = Intent(this, OrderTrackingActivity::class.java)
            // startActivity(intent)
        }

        // Handle "Back to Home" button click
        backToHomeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // Clear the back stack
            startActivity(intent)
            finish() // Close the current activity
        }
    }
}
