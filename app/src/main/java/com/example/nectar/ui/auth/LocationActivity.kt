package com.example.nectar.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.example.nectar.R


class LocationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_location)

        // List of zones and areas
        val zones = listOf("Stadium", "Gulshan", "North Nazimabad", "Shah Faisal", "Model Colony")
        val areas = listOf("District Korangi", "District Malir", "District South", "District North")

        // Find the AutoCompleteTextView for "Your Zone"
        val autoCompleteZone = findViewById<AutoCompleteTextView>(R.id.autoCompleteZone)
        // Set up the adapter for "Your Zone"
        val zoneAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, zones)
        autoCompleteZone.setAdapter(zoneAdapter)

        // Find the AutoCompleteTextView for "Your Area"
        val autoCompleteArea = findViewById<AutoCompleteTextView>(R.id.autoCompleteArea)
        // Set up the adapter for "Your Area"
        val areaAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, areas)
        autoCompleteArea.setAdapter(areaAdapter)

        // Submit Button
        val submitButton = findViewById<Button>(R.id.SubmitButton)
        submitButton.setOnClickListener {
            val intent = Intent(this,  LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Go Back Button
        val goBackButton = findViewById<ImageButton>(R.id.goBackButton)
        goBackButton.setOnClickListener {
            val intent = Intent(this, VerificationActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Handle edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}