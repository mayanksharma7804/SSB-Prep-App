package com.ssbprep.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ssbprep.databinding.ActivityInterviewBinding
import com.ssbprep.viewmodel.PaymentViewModel

class InterviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInterviewBinding
    private val viewModel: PaymentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInterviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener { finish() }

        binding.scheduleInterviewButton.setOnClickListener {
            checkPaymentAndNavigate()
        }

        // Module Listeners
        binding.prepGuideButton.setOnClickListener {
            val intent = Intent(this, ServiceDetailActivity::class.java)
            intent.putExtra("SERVICE_TYPE", "INTERVIEW_GUIDE")
            startActivity(intent)
        }
        
        binding.ciqButton.setOnClickListener {
            startActivity(Intent(this, CIQsActivity::class.java))
        }

        binding.swotButton.setOnClickListener {
            val intent = Intent(this, ServiceDetailActivity::class.java)
            intent.putExtra("SERVICE_TYPE", "SWOT")
            startActivity(intent)
        }
    }

    private fun checkPaymentAndNavigate() {
        if (viewModel.isPaid.value) {
            startActivity(Intent(this@InterviewActivity, InterviewBookingActivity::class.java))
        } else {
            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("USER_ID", viewModel.getUserId())
            startActivity(intent)
        }
    }
}
