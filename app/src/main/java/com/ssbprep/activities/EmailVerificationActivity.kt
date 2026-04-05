package com.ssbprep.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.ssbprep.databinding.ActivityEmailVerificationBinding

class EmailVerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailVerificationBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val email = intent.getStringExtra("EMAIL") ?: "your email"
        binding.verificationMessage.text = "We've sent a verification link to $email. Please click it to continue."

        binding.checkStatusButton.setOnClickListener {
            checkVerificationStatus()
        }

        binding.resendEmailText.setOnClickListener {
            resendVerificationEmail()
        }
    }

    private fun checkVerificationStatus() {
        showLoading(true)
        val user = auth.currentUser
        // Reload the user to get the latest emailVerified status from Firebase
        user?.reload()?.addOnCompleteListener { task ->
            showLoading(false)
            if (task.isSuccessful) {
                if (user.isEmailVerified) {
                    Toast.makeText(this, "Email Verified! Welcome to SSB Prep.", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Email not verified yet. Please check your inbox.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Error checking status: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun resendVerificationEmail() {
        showLoading(true)
        auth.currentUser?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                showLoading(false)
                if (task.isSuccessful) {
                    Toast.makeText(this, "Verification email resent.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error resending: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingOverlay.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.checkStatusButton.isEnabled = !isLoading
    }
}
