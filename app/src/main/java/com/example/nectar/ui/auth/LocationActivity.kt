package com.example.nectar.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nectar.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class LocationActivity : AppCompatActivity() {

    // Firebase instances
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    // UI Adapters
    private lateinit var zoneAdapter: ArrayAdapter<String>
    private lateinit var areaAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        // Initialize UI components
        initUI()

        // Load initial data
        loadZones()
    }

    private fun initUI() {
        // Setup dropdown adapters
        zoneAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, mutableListOf())
        areaAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, mutableListOf())

        val autoCompleteZone = findViewById<AutoCompleteTextView>(R.id.autoCompleteZone).apply {
            setAdapter(zoneAdapter)
            setOnItemClickListener { _, _, position, _ ->
                zoneAdapter.getItem(position)?.let { loadAreas(it) }
            }
        }

        findViewById<AutoCompleteTextView>(R.id.autoCompleteArea).apply {
            setAdapter(areaAdapter)
        }

        // Button click handlers
        findViewById<Button>(R.id.SubmitButton).setOnClickListener {
            val zone = autoCompleteZone.text.toString().trim()
            val area = findViewById<AutoCompleteTextView>(R.id.autoCompleteArea).text.toString().trim()

            if (validateInput(zone, area)) {
                saveLocation(zone, area)
            }
        }

        findViewById<ImageButton>(R.id.goBackButton).setOnClickListener {
            finish()
        }
    }

    private fun loadZones() {
        db.collection("locations").document("zones")
            .get()
            .addOnSuccessListener { document ->
                (document.get("list") as? List<*>)?.filterIsInstance<String>()?.let { zones ->
                    zoneAdapter.clear()
                    zoneAdapter.addAll(zones)
                } ?: showToast("Invalid zone data format")
            }
            .addOnFailureListener { e ->
                showToast("Failed to load zones")
                Log.e("LocationActivity", "Zone load error", e)
            }
    }

    private fun loadAreas(zone: String) {
        db.collection("locations").document("areas")
            .get()
            .addOnSuccessListener { document ->
                (document.data?.get(zone) as? List<*>)?.filterIsInstance<String>()?.let { areas ->
                    areaAdapter.clear()
                    areaAdapter.addAll(areas)
                } ?: showToast("No areas found for $zone")
            }
            .addOnFailureListener { e ->
                showToast("Failed to load areas")
                Log.e("LocationActivity", "Area load error", e)
            }
    }

    private fun validateInput(zone: String, area: String): Boolean {
        return when {
            zone.isEmpty() -> {
                showToast("Please select a zone")
                false
            }
            area.isEmpty() -> {
                showToast("Please select an area")
                false
            }
            else -> true
        }
    }

    private fun saveLocation(zone: String, area: String) {
        val userId = auth.currentUser?.uid ?: run {
            showToast("User not authenticated")
            return
        }

        val locationData = hashMapOf(
            "zone" to zone,
            "area" to area,
            "lastUpdated" to FieldValue.serverTimestamp()
        )

        // This will create or update the document
        db.collection("users").document(userId)
            .set(locationData, SetOptions.merge())
            .addOnSuccessListener {
                showToast("Location saved successfully")
                navigateToHome()
            }
            .addOnFailureListener { e ->
                showToast("Failed to save location")
                Log.e("LocationActivity", "Save error", e)
            }
    }

    private fun navigateToHome() {
        startActivity(Intent(this, SignupActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}