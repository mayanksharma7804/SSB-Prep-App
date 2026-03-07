package com.ssbprep.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.GoogleAuthProvider
import com.ssbprep.R
import com.ssbprep.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            startHomeActivity()
            return
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.googleSignInButton.setOnClickListener { signInWithGoogle() }
        
        binding.loginButton.setOnClickListener {
            val email = binding.usernameField.text.toString().trim()
            val password = binding.passwordField.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            showLoading(true)
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        startHomeActivity()
                    } else {
                        showLoading(false)
                        handleFirebaseAuthError(task.exception)
                    }
                }
        }

        binding.signUpText.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.forgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }

    private fun handleFirebaseAuthError(exception: Exception?) {
        val message = when (exception) {
            is FirebaseAuthInvalidUserException -> {
                if (exception.errorCode == "ERROR_USER_NOT_FOUND") {
                    "No account found with this email. Please Sign Up first."
                } else {
                    "This account has been disabled."
                }
            }
            is FirebaseAuthInvalidCredentialsException -> "Incorrect password. Please try again."
            is FirebaseNetworkException -> "Internet not working. Please check your connection."
            else -> "Internal Error: App might be under maintenance. Please try later."
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingOverlay.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.loginCard.alpha = if (isLoading) 0.5f else 1.0f
        binding.loginButton.isEnabled = !isLoading
        binding.googleSignInButton.isEnabled = !isLoading
    }

    private fun signInWithGoogle() {
        showLoading(true)
        googleSignInClient.signOut().addOnCompleteListener {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
    }

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                showLoading(false)
                handleGoogleSignInError(e)
            }
        } else {
            showLoading(false)
            Toast.makeText(this, "Sign-In cancelled.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleGoogleSignInError(e: ApiException) {
        val message = when (e.statusCode) {
            7 -> "Network Error: Please check your internet connection."
            10 -> "Developer Error: SHA-1 or Web Client ID mismatch in Firebase console."
            12500 -> "Sign-in failed. Please update Google Play Services."
            12501 -> "Sign-in cancelled by user."
            CommonStatusCodes.NETWORK_ERROR -> "No internet connection."
            else -> "Google Sign-In failed (Error Code: ${e.statusCode})"
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startHomeActivity()
                } else {
                    showLoading(false)
                    handleFirebaseAuthError(task.exception)
                }
            }
    }

    private fun startHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
