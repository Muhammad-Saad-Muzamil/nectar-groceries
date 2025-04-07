package com.example.nectar.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nectar.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class VerificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_verification)
        try {
            val fabNext: FloatingActionButton = findViewById(R.id.fabNext)
            val goBackButton: ImageButton = findViewById(R.id.goBackButton)

            fabNext.setOnClickListener {
                startActivity(Intent(this, LocationActivity::class.java))
                finish()
            }

            goBackButton.setOnClickListener {
                startActivity(Intent(this, NumberActivity::class.java))
                finish()
            }


            val mainLayout = findViewById<android.view.View>(R.id.main)
            if (mainLayout != null) {
                ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
                    val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                    insets
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}
