package com.ssbprep.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.ssbprep.BuildConfig
import com.ssbprep.R
import org.json.JSONObject

class PaymentActivity : AppCompatActivity(), PaymentResultListener {

    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        userId = intent.getStringExtra("USER_ID")
        
        Checkout.preload(applicationContext)

        val payButton = findViewById<com.google.android.material.button.MaterialButton>(R.id.payNowButton)
        payButton.setOnClickListener {
            startPayment()
        }

        val backButton = findViewById<android.widget.ImageButton>(R.id.backButton)
        backButton.setOnClickListener { finish() }
    }

    private fun startPayment() {
        val checkout = Checkout()
        checkout.setKeyID(BuildConfig.RAZORPAY_KEY_ID)

        try {
            val options = JSONObject()
            options.put("name", "SSB Master")
            options.put("description", "Mock Interview Session")
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("theme.color", "#4285F4")
            options.put("currency", "INR")
            options.put("amount", "29900") // ₹299.00
            
            val notes = JSONObject()
            notes.put("userId", userId ?: "unknown")
            options.put("notes", notes)

            checkout.open(this, options)
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onPaymentSuccess(razorpayPaymentId: String?) {
        Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show()
        // Navigate to booking details page after successful payment
        val intent = Intent(this, InterviewBookingActivity::class.java)
        intent.putExtra("USER_ID", userId)
        startActivity(intent)
        finish()
    }

    override fun onPaymentError(code: Int, response: String?) {
        Toast.makeText(this, "Payment Failed: $response", Toast.LENGTH_LONG).show()
    }
}
