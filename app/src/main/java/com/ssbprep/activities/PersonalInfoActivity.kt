package com.ssbprep.activities

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ssbprep.databinding.ActivityPersonalInfoBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PersonalInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonalInfoBinding
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val userId = auth.currentUser?.uid

    private var selectedHeight = 170
    private var selectedWeight = 65

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener { finish() }
        binding.cancelButton.setOnClickListener { finish() }

        setupPickers()
        setupEduSpinners()
        setupSSBAttemptsSpinner()
        loadUserData()

        binding.saveButton.setOnClickListener {
            saveUserData()
        }
    }

    private fun setupPickers() {
        binding.heightField.setOnClickListener {
            showNumberPickerDialog("Select Height (cm)", 140, 210, selectedHeight) {
                selectedHeight = it
                binding.heightField.setText("$it cm")
            }
        }

        binding.weightField.setOnClickListener {
            showNumberPickerDialog("Select Weight (kg)", 40, 120, selectedWeight) {
                selectedWeight = it
                binding.weightField.setText("$it kg")
            }
        }
    }

    private fun showNumberPickerDialog(title: String, min: Int, max: Int, current: Int, onPick: (Int) -> Unit) {
        val picker = NumberPicker(this).apply {
            minValue = min
            maxValue = max
            value = current
        }
        AlertDialog.Builder(this)
            .setTitle(title)
            .setView(picker)
            .setPositiveButton("Set") { _, _ -> onPick(picker.value) }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun setupEduSpinners() {
        val eduLevels = arrayOf("Select Education", "12th", "Graduation", "Graduated")
        val levelAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, eduLevels)
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.eduLevelSpinner.adapter = levelAdapter

        binding.eduLevelSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selected = eduLevels[position]
                if (position == 0) {
                    binding.eduStatusSpinner.visibility = View.GONE
                    return
                }

                binding.eduStatusSpinner.visibility = View.VISIBLE
                val statusOptions = when (selected) {
                    "12th" -> arrayOf("Appearing", "Passed")
                    "Graduation" -> arrayOf("Prefinal Year", "Final Year")
                    "Graduated" -> arrayOf("Working", "Self-Employed", "Student", "Gap Year")
                    else -> emptyArray()
                }

                val statusAdapter = ArrayAdapter(this@PersonalInfoActivity, android.R.layout.simple_spinner_item, statusOptions)
                statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.eduStatusSpinner.adapter = statusAdapter
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupSSBAttemptsSpinner() {
        val attempts = (0..23).map { it.toString() }.toTypedArray()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, attempts)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.ssbAttemptsSpinner.adapter = adapter
    }

    private fun loadUserData() {
        if (userId == null) return
        lifecycleScope.launch {
            try {
                val document = db.collection("users").document(userId).get().await()
                if (document.exists()) {
                    selectedHeight = document.getLong("height")?.toInt() ?: 170
                    selectedWeight = document.getLong("weight")?.toInt() ?: 65
                    binding.heightField.setText("$selectedHeight cm")
                    binding.weightField.setText("$selectedWeight kg")

                    val eduLevel = document.getString("eduLevel")
                    val eduStatus = document.getString("eduStatus")
                    val ssbAttempts = document.getString("ssbAttempts")

                    if (eduLevel != null) {
                        val pos = (binding.eduLevelSpinner.adapter as ArrayAdapter<String>).getPosition(eduLevel)
                        if (pos >= 0) {
                            binding.eduLevelSpinner.setSelection(pos)
                            binding.eduLevelSpinner.post {
                                if (eduStatus != null && binding.eduStatusSpinner.adapter != null) {
                                    val sPos = (binding.eduStatusSpinner.adapter as ArrayAdapter<String>).getPosition(eduStatus)
                                    if (sPos >= 0) binding.eduStatusSpinner.setSelection(sPos)
                                }
                            }
                        }
                    }

                    if (ssbAttempts != null) {
                        val pos = (binding.ssbAttemptsSpinner.adapter as ArrayAdapter<String>).getPosition(ssbAttempts)
                        if (pos >= 0) binding.ssbAttemptsSpinner.setSelection(pos)
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@PersonalInfoActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserData() {
        if (userId == null) return
        val eduLevel = binding.eduLevelSpinner.selectedItem.toString()
        if (eduLevel == "Select Education") {
            Toast.makeText(this, "Please select education level", Toast.LENGTH_SHORT).show()
            return
        }

        val data = hashMapOf(
            "height" to selectedHeight,
            "weight" to selectedWeight,
            "eduLevel" to eduLevel,
            "eduStatus" to binding.eduStatusSpinner.selectedItem?.toString(),
            "ssbAttempts" to binding.ssbAttemptsSpinner.selectedItem.toString(),
            "updatedAt" to com.google.firebase.firestore.FieldValue.serverTimestamp()
        )

        lifecycleScope.launch {
            try {
                db.collection("users").document(userId).set(data, com.google.firebase.firestore.SetOptions.merge()).await()
                Toast.makeText(this@PersonalInfoActivity, "Saved!", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@PersonalInfoActivity, "Save failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
