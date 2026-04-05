package com.ssbprep.activities

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.FirebaseFirestore
import com.ssbprep.R
import com.ssbprep.databinding.ActivityInterviewBookingBinding
import com.ssbprep.viewmodel.PaymentViewModel
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class InterviewBookingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInterviewBookingBinding
    private val viewModel: PaymentViewModel by viewModels()
    private var selectedDateStr: String? = null
    private val db = FirebaseFirestore.getInstance()
    private val bookedSlotsForSelectedDate = mutableSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInterviewBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinners()
        setupCalendar()

        binding.backButton.setOnClickListener { finish() }

        // Success Overlay Button
        binding.doneButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // Underline "Download PIQ Form"
        binding.downloadPIQButton.paintFlags = binding.downloadPIQButton.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.downloadPIQButton.setOnClickListener {
            val piqUrl = "https://drive.google.com/file/d/1f-xWT6h5XyKkL6jZxZr0tJz7VGMUzagJ/view?usp=drive_link"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(piqUrl))
            startActivity(intent)
        }

        // Restore Upload UI
        binding.uploadButton.visibility = View.VISIBLE
        binding.uploadButton.setOnClickListener {
            selectPiqPdf()
        }

        binding.submitBookingButton.setOnClickListener {
            submitBookingDetails()
        }

        // Entry Type "Other" logic
        binding.entrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selected = parent?.getItemAtPosition(position).toString()
                binding.otherEntryField.visibility = if (selected == "Other") View.VISIBLE else View.GONE
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupSpinners() {
        val entryTypes = resources.getStringArray(R.array.entry_types)
        val entryAdapter = object : ArrayAdapter<String>(this, R.layout.spinner_item, entryTypes) {
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.card_dark))
                return view
            }
        }
        binding.entrySpinner.adapter = entryAdapter

        updateTimeSlotSpinner()
    }

    private fun updateTimeSlotSpinner() {
        val allSlots = resources.getStringArray(R.array.time_slots).toMutableList()
        val availableSlots = allSlots.filter { !bookedSlotsForSelectedDate.contains(it) }
        
        val timeAdapter = object : ArrayAdapter<String>(this, R.layout.spinner_item, availableSlots) {
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.card_dark))
                return view
            }
        }
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
                bookedSlotsForSelectedDate.clear()
                updateTimeSlotSpinner()
                return@setOnDateChangeListener
            }

            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            selectedDateStr = sdf.format(selectedCal.time)
            
            fetchBookedSlots(selectedDateStr!!)
        }
    }

    private fun fetchBookedSlots(date: String) {
        db.collection("interview_bookings")
            .whereEqualTo("date", date)
            .get()
            .addOnSuccessListener { documents ->
                bookedSlotsForSelectedDate.clear()
                for (doc in documents) {
                    val slot = doc.getString("timeSlot")
                    if (slot != null) bookedSlotsForSelectedDate.add(slot)
                }
                updateTimeSlotSpinner()
            }
            .addOnFailureListener {
                bookedSlotsForSelectedDate.clear()
                updateTimeSlotSpinner()
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
        if (result.resultCode == RESULT_OK) {
            binding.fileNameText.text = "PIQ Selected"
        }
    }

    private fun submitBookingDetails() {
        val name = binding.nameField.text.toString().trim()
        val phone = binding.phoneField.text.toString().trim()
        val entryType = binding.entrySpinner.selectedItem?.toString() ?: ""
        val otherEntry = binding.otherEntryField.text.toString().trim()
        val timeSlot = binding.timeSlotSpinner.selectedItem?.toString()
        
        if (name.isEmpty() || phone.isEmpty() || selectedDateStr == null || timeSlot == null) {
            Toast.makeText(this, "Please complete all fields and select a slot.", Toast.LENGTH_SHORT).show()
            return
        }

        if (entryType == "Other" && otherEntry.isEmpty()) {
            Toast.makeText(this, "Please specify your entry type.", Toast.LENGTH_SHORT).show()
            return
        }

        binding.submitBookingButton.isEnabled = false
        binding.submitBookingButton.text = "Saving Details..."

        lifecycleScope.launch {
            try {
                val userId = viewModel.getUserId() ?: "unknown"
                val entryToSave = if (entryType == "Other") otherEntry else entryType
                
                val bookingData = hashMapOf(
                    "userId" to userId,
                    "name" to name,
                    "phone" to phone,
                    "entry" to entryToSave,
                    "date" to selectedDateStr,
                    "timeSlot" to timeSlot,
                    "timestamp" to com.google.firebase.Timestamp.now(),
                    "status" to "Pending Confirmation"
                )

                db.collection("interview_bookings").add(bookingData).await()
                
                val userRef = db.collection("users").document(userId)
                userRef.update("isPaid", false).await()
                
                binding.successOverlay.visibility = View.VISIBLE
                
            } catch (e: Exception) {
                binding.submitBookingButton.isEnabled = true
                binding.submitBookingButton.text = "Submit Details"
                Toast.makeText(this@InterviewBookingActivity, "Failed: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
