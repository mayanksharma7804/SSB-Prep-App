package com.ssbprep.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ssbprep.databinding.ActivityInterviewBinding
import com.ssbprep.viewmodel.PaymentViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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

        binding.ciqButton.setOnClickListener {
            startActivity(Intent(this, CIQsActivity::class.java))
        }

        binding.introButton.setOnClickListener {
            val intent = Intent(this, TaskDetailActivity::class.java).apply {
                putExtra("title", "Interview Introduction")
                putExtra("description", "The Personal Interview is a one-on-one interaction with the Interviewing Officer (IO).")
                putExtra("tips", "• Be honest and consistent with your PIQ.\n• Maintain good posture and eye contact.\n• Listen carefully before answering.")
            }
            startActivity(intent)
        }
    }

    private fun checkPaymentAndNavigate() {
        lifecycleScope.launch {
            viewModel.isPaid.collectLatest { isPaid ->
                if (isPaid) {
                    startActivity(Intent(this@InterviewActivity, InterviewBookingActivity::class.java))
                } else {
                    showPayNowPopup()
                }
            }
        }
    }

    private fun showPayNowPopup() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Premium Feature")
            .setMessage("To schedule a 1-on-1 mock interview with a recommended candidate, a one-time fee of ₹299 is required.")
            .setNegativeButton("Maybe Later", null)
            .setPositiveButton("Pay Now") { _, _ ->
                val intent = Intent(this, PaymentActivity::class.java)
                intent.putExtra("USER_ID", viewModel.getUserId())
                startActivity(intent)
            }
            .show()
    }
}
