package com.example.nectar.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nectar.MainActivity
import com.example.nectar.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignupActivity : AppCompatActivity() {

    // Initialize FirebaseAuth instance
    private lateinit var auth: FirebaseAuth

    // UI elements
    private lateinit var usernameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var signupButton: Button
    private lateinit var switchToLoginText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Bind UI elements
        usernameEditText = findViewById(R.id.signupUsernameEditText)
        emailEditText = findViewById(R.id.signupEmailEditText)
        passwordEditText = findViewById(R.id.signupPasswordEditText)
        signupButton = findViewById(R.id.signupButton)
        switchToLoginText = findViewById(R.id.loginText)

        // Set up the signup button click listener
        signupButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty()) {
                // Call Firebase signup method
                signupUser(email, password)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Switch to Login screen
        switchToLoginText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Function to create a user using Firebase Auth
    private fun signupUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign-up success, update UI with the signed-in user's information
                    val user: FirebaseUser? = auth.currentUser
                    if (user != null) {
                        // Here, you can save the user data like the username in your database
                        Toast.makeText(this, "Sign Up Successful!", Toast.LENGTH_SHORT).show()

                        // Navigate to MainActivity
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    // If sign-up fails, display a message to the user
                    Toast.makeText(this, "Sign Up Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
