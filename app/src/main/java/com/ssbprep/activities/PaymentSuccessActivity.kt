package com.ssbprep.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssbprep.databinding.ActivityPaymentSuccessBinding

class PaymentSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val transactionId = intent.getStringExtra("TRANSACTION_ID") ?: "N/A"
        binding.transactionIdText.text = transactionId

        binding.doneButton.setOnClickListener {
            // Direct navigation to Booking Details page instead of Home
            val intent = Intent(this, InterviewBookingActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
