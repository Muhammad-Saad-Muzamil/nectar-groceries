package com.example.nectar.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nectar.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hbb20.CountryCodePicker
import android.widget.EditText

class NumberActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number)

        val fabNext: FloatingActionButton = findViewById(R.id.fabNext)
        val goBackButton: ImageButton = findViewById(R.id.goBackButton)
        val countryCodePicker: CountryCodePicker = findViewById(R.id.countryCodePicker)
        val etPhone: EditText = findViewById(R.id.etPhone)

        // Handle go back button click
        goBackButton.setOnClickListener {
            startActivity(Intent(this, SigninActivity::class.java))
            finish()
        }

        // Handle next button click to proceed to verification activity
        fabNext.setOnClickListener {
            val phoneNumber = countryCodePicker.selectedCountryCodeWithPlus + etPhone.text.toString()

            if (TextUtils.isEmpty(phoneNumber)) {
                Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Pass phone number to VerificationActivity
            val intent = Intent(this, VerificationActivity::class.java)
            intent.putExtra("phone_number", phoneNumber)
            startActivity(intent)
            finish()
        }

        // Handle window insets for proper layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
