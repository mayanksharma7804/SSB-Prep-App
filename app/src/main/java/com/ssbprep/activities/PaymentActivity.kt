package com.ssbprep.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.ssbprep.BuildConfig
import com.ssbprep.R
import com.ssbprep.databinding.ActivityPaymentBinding
import com.ssbprep.viewmodel.PaymentViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.json.JSONObject

class PaymentActivity : AppCompatActivity(), PaymentResultListener {

    private lateinit var binding: ActivityPaymentBinding
    private val viewModel: PaymentViewModel by viewModels()
    private var userId: String? = null
    private var selectedMethod: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = viewModel.getUserId()
        
        Checkout.preload(applicationContext)

        // Initialize state: nothing checked
        binding.upiRadioButton.isChecked = false
        binding.cardRadioButton.isChecked = false
        binding.payNowButton.isEnabled = false
        binding.payNowButton.alpha = 0.5f

        // Set up manual RadioGroup-like behavior for CardViews
        binding.upiOptionCard.setOnClickListener {
            selectPaymentMethod("UPI")
        }

        binding.cardOptionCard.setOnClickListener {
            selectPaymentMethod("CARD")
        }

        binding.payNowButton.setOnClickListener {
            startPayment()
        }

        binding.backButton.setOnClickListener { finish() }

        // We still observe payment status in case the server is extremely fast
        // but the main transition happens in onPaymentSuccess for better UX
        lifecycleScope.launch {
            viewModel.isPaid.collectLatest { isPaid ->
                if (isPaid && binding.payNowButton.text == "Confirming...") {
                    navigateToSuccess("Unlocked via Server")
                }
            }
        }
    }

    private fun selectPaymentMethod(method: String) {
        selectedMethod = method
        
        binding.upiRadioButton.isChecked = (method == "UPI")
        binding.cardRadioButton.isChecked = (method == "CARD")
        
        binding.upiOptionCard.setCardBackgroundColor(
            if (method == "UPI") getColor(R.color.card_dark) else getColor(R.color.bg_dark)
        )
        binding.cardOptionCard.setCardBackgroundColor(
            if (method == "CARD") getColor(R.color.card_dark) else getColor(R.color.bg_dark)
        )
        
        binding.upiOptionCard.strokeColor = if (method == "UPI") getColor(R.color.primary_blue) else getColor(R.color.glass_border)
        binding.cardOptionCard.strokeColor = if (method == "CARD") getColor(R.color.primary_blue) else getColor(R.color.glass_border)
        binding.upiOptionCard.strokeWidth = if (method == "UPI") 4 else 2
        binding.cardOptionCard.strokeWidth = if (method == "CARD") 4 else 2

        binding.payNowButton.isEnabled = true
        binding.payNowButton.alpha = 1.0f
    }

    private fun startPayment() {
        val checkout = Checkout()
        checkout.setKeyID(BuildConfig.RAZORPAY_KEY_ID)

        try {
            val options = JSONObject()
            options.put("name", "SSB Prep Premium")
            options.put("description", "Unlock 1-on-1 Mock Interview")
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("theme.color", "#007AFF")
            options.put("currency", "INR")
            options.put("amount", "29900")
            
            val prefill = JSONObject()
            if (selectedMethod == "UPI") {
                prefill.put("method", "upi")
            }
            options.put("prefill", prefill)

            val notes = JSONObject()
            notes.put("userId", userId ?: "unknown")
            options.put("notes", notes)

            checkout.open(this, options)
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.payNowButton.isEnabled = !isLoading
        binding.payNowButton.text = if (isLoading) "Confirming..." else "Complete Payment"
    }

    override fun onPaymentSuccess(razorpayPaymentId: String?) {
        // Immediate visual feedback: Navigate to success screen
        navigateToSuccess(razorpayPaymentId ?: "TXN_SUCCESS")
    }

    override fun onPaymentError(code: Int, response: String?) {
        showLoading(false)
        Toast.makeText(this, "Payment Failed or Cancelled", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToSuccess(txnId: String) {
        val intent = Intent(this, PaymentSuccessActivity::class.java)
        intent.putExtra("TRANSACTION_ID", txnId)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
