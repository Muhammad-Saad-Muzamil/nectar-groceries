package com.example.nectar.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.nectar.MainActivity
import com.example.nectar.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hbb20.CountryCodePicker

class SigninActivity : AppCompatActivity() {

    private companion object {
        const val TAG = "SigninActivity"
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            RESULT_OK -> handleSuccessfulSignIn(result.data)
            RESULT_CANCELED -> handleSignInCancellation()
            else -> handleSignInFailure(result)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        findViewById<com.google.android.material.button.MaterialButton>(R.id.Google).setOnClickListener {
            signInWithGoogle()
        }

        val countryCodePicker: CountryCodePicker = findViewById(R.id.countryCodePicker)
        countryCodePicker.setCcpClickable(false)
        countryCodePicker.setOnClickListener {
            startActivity(Intent(this, NumberActivity::class.java))
        }
    }

    private fun signInWithGoogle() {
        try {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        } catch (e: Exception) {
            handleSignInError(
                statusCode = GoogleSignInStatusCodes.SIGN_IN_FAILED,
                exception = e,
                message = "Failed to start Google Sign-In"
            )
        }
    }

    private fun handleSuccessfulSignIn(data: Intent?) {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            account?.idToken?.let { token ->
                firebaseAuthWithGoogle(token)
            } ?: run {
                handleSignInError(
                    statusCode = GoogleSignInStatusCodes.SIGN_IN_FAILED,
                    message = "No ID token received from Google"
                )
            }
        } catch (e: ApiException) {
            handleSignInError(e.statusCode, e)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    navigateToMainActivity()
                } else {
                    handleSignInError(
                        statusCode = GoogleSignInStatusCodes.SIGN_IN_FAILED,
                        exception = task.exception,
                        message = "Firebase authentication failed"
                    )
                }
            }
    }

    private fun handleSignInCancellation() {
        showToast("Sign in canceled")
    }

    private fun handleSignInFailure(result: androidx.activity.result.ActivityResult) {
        val exception = result.data?.let {
            GoogleSignIn.getSignedInAccountFromIntent(it).exception
        }
        val statusCode = (exception as? ApiException)?.statusCode ?: GoogleSignInStatusCodes.SIGN_IN_FAILED

        handleSignInError(
            statusCode = statusCode,
            exception = exception,
            message = "Sign in failed"
        )
    }

    private fun handleSignInError(
        statusCode: Int,
        exception: Exception? = null,
        message: String? = null
    ) {
        val errorMessage = when (statusCode) {
            GoogleSignInStatusCodes.SIGN_IN_CANCELLED -> "Sign in canceled"
            GoogleSignInStatusCodes.SIGN_IN_FAILED -> message ?: "Sign in failed"
            GoogleSignInStatusCodes.NETWORK_ERROR -> "Network error occurred"
            else -> message ?: "Unknown error occurred"
        }

        showToast(errorMessage)
        Log.e(TAG, "Sign-In Error: $errorMessage", exception)
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}