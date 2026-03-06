package com.ssbprep.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.ssbprep.R
import com.ssbprep.databinding.ActivityInterviewBookingBinding
import com.ssbprep.viewmodel.PaymentViewModel
import java.text.SimpleDateFormat
import java.util.*

class InterviewBookingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInterviewBookingBinding
    private val viewModel: PaymentViewModel by viewModels()
    private var piqFileUri: Uri? = null
    private var selectedDateStr: String? = null
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInterviewBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinners()
        setupCalendar()

        binding.backButton.setOnClickListener { finish() }

        binding.uploadButton.setOnClickListener {
            selectPiqPdf()
        }

        binding.submitBookingButton.setOnClickListener {
            submitBookingDetails()
        }
    }

    private fun setupSpinners() {
        val entryTypes = resources.getStringArray(R.array.entry_types)
        val entryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, entryTypes)
        entryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.entrySpinner.adapter = entryAdapter

        val timeSlots = resources.getStringArray(R.array.time_slots)
        val timeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, timeSlots)
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.timeSlotSpinner.adapter = timeAdapter
    }

    private fun setupCalendar() {
        val calendar = Calendar.getInstance()
        binding.calendarView.minDate = calendar.timeInMillis

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedCal = Calendar.getInstance()
            selectedCal.set(year, month, dayOfMonth)
            
            val dayOfWeek = selectedCal.get(Calendar.DAY_OF_WEEK)
            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                Toast.makeText(this, "Interviews are only available Monday to Friday", Toast.LENGTH_SHORT).show()
                selectedDateStr = null
                return@setOnDateChangeListener
            }

            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            selectedDateStr = sdf.format(selectedCal.time)
            
            checkAvailability(selectedDateStr!!)
        }
    }

    private fun checkAvailability(date: String) {
        db.collection("interviews")
            .whereEqualTo("date", date)
            .get()
            .addOnSuccessListener { documents ->
                val count = documents.size()
                when {
                    count >= 2 -> {
                        Toast.makeText(this, "This date is Full (RED). Please select another date.", Toast.LENGTH_SHORT).show()
                    }
                    count == 1 -> {
                        Toast.makeText(this, "1 Slot Available (ORANGE) for this date.", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(this, "Date is Available (GREEN).", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun selectPiqPdf() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "application/pdf"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        pdfPickerLauncher.launch(intent)
    }

    private val pdfPickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                piqFileUri = uri
                val cursor = contentResolver.query(uri, null, null, null, null)
                cursor?.use { c ->
                    if (c.moveToFirst()) {
                        val nameIndex = c.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        if (nameIndex != -1) {
                            binding.fileNameText.text = c.getString(nameIndex)
                        }
                    }
                }
            }
        }
    }

    private fun submitBookingDetails() {
        val name = binding.nameField.text.toString().trim()
        val phone = binding.phoneField.text.toString().trim()
        
        if (name.isEmpty() || phone.isEmpty() || selectedDateStr == null || piqFileUri == null) {
            Toast.makeText(this, "Please complete all fields and upload PIQ.", Toast.LENGTH_SHORT).show()
            return
        }

        binding.submitBookingButton.isEnabled = false
        binding.submitBookingButton.text = "Submitting..."

        val storageRef = FirebaseStorage.getInstance().reference
        val piqRef = storageRef.child("piqs/${viewModel.getUserId()}_${System.currentTimeMillis()}.pdf")

        piqRef.putFile(piqFileUri!!)
            .addOnSuccessListener {
                piqRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    saveToFirestore(name, phone, downloadUri.toString())
                }
            }
            .addOnFailureListener { e ->
                binding.submitBookingButton.isEnabled = true
                binding.submitBookingButton.text = "Submit Details"
                Toast.makeText(this, "Upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveToFirestore(name: String, phone: String, downloadUrl: String) {
        val bookingData = hashMapOf(
            "userId" to viewModel.getUserId(),
            "name" to name,
            "phone" to phone,
            "entry" to binding.entrySpinner.selectedItem.toString(),
            "date" to selectedDateStr,
            "timeSlot" to binding.timeSlotSpinner.selectedItem.toString(),
            "piqUrl" to downloadUrl,
            "timestamp" to System.currentTimeMillis(),
            "status" to "Pending Confirmation"
        )

        db.collection("interviews")
            .add(bookingData)
            .addOnSuccessListener {
                showSuccessDialog()
            }
            .addOnFailureListener { e ->
                binding.submitBookingButton.isEnabled = true
                binding.submitBookingButton.text = "Submit Details"
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showSuccessDialog() {
        com.google.android.material.dialog.MaterialAlertDialogBuilder(this)
            .setTitle("Booking Successful")
            .setMessage("Your interview details have been submitted. We will contact you soon with the interview confirmation.")
            .setPositiveButton("OK") { _, _ ->
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
            .setCancelable(false)
            .show()
    }
}
