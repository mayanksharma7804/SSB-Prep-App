package com.ssbprep.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.ssbprep.R
import com.ssbprep.databinding.ActivityProfileSettingsBinding
import com.ssbprep.utils.LocaleHelper
import com.ssbprep.utils.ThemeManager

class ProfileSettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileSettingsBinding
    private lateinit var auth: FirebaseAuth
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        binding.backButton.setOnClickListener { finish() }

        // Load current user data
        if (user != null) {
            val fullName = user.displayName
            val firstName = if (!fullName.isNullOrEmpty()) fullName.split(" ")[0] else "User"
            binding.nameField.setText(fullName ?: "User")
            binding.emailField.setText(user.email)
            
            // Load profile image
            Glide.with(this)
                .load(user.photoUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .circleCrop()
                .into(binding.profileImage)
        }

        binding.editProfileButton.setOnClickListener {
            toggleEditing(true)
        }

        binding.editImageButton.setOnClickListener {
            pickImage()
        }

        binding.saveButton.setOnClickListener {
            saveChanges()
        }

        // Theme Toggle
        updateThemeIcon()
        binding.themeToggleButton.setOnClickListener {
            ThemeManager.toggleTheme(this)
            updateThemeIcon()
            recreate() // Apply theme change
        }

        // Language Selection
        updateLanguageText()
        binding.languageLayout.setOnClickListener {
            showLanguageDialog()
        }

        binding.privacyPolicyLayout.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ssbprep.com/privacy-policy"))
            startActivity(browserIntent)
        }

        binding.termsLayout.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ssbprep.com/terms"))
            startActivity(browserIntent)
        }
    }

    private fun showLanguageDialog() {
        val languages = arrayOf("English", "Hindi (हिन्दी)")
        val langCodes = arrayOf("en", "hi")
        
        val currentLang = LocaleHelper.getLanguage(this)
        val checkedItem = langCodes.indexOf(currentLang)

        AlertDialog.Builder(this)
            .setTitle("Choose Language")
            .setSingleChoiceItems(languages, checkedItem) { dialog, which ->
                LocaleHelper.setLocale(this, langCodes[which])
                dialog.dismiss()
                updateLanguageText()
                recreate() // Apply language change across app
            }
            .show()
    }

    private fun updateLanguageText() {
        val currentLang = LocaleHelper.getLanguage(this)
        binding.selectedLanguage.text = if (currentLang == "hi") "Hindi (हिन्दी)" else "English"
    }

    private fun updateThemeIcon() {
        if (ThemeManager.isDarkMode(this)) {
            binding.themeToggleButton.setImageResource(R.drawable.ic_sun)
        } else {
            binding.themeToggleButton.setImageResource(R.drawable.ic_moon)
        }
    }

    private fun toggleEditing(isEditing: Boolean) {
        binding.nameField.isEnabled = isEditing
        binding.editProfileButton.visibility = if (isEditing) View.GONE else View.VISIBLE
        binding.saveButton.visibility = if (isEditing) View.VISIBLE else View.GONE
    }

    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedImageUri = result.data?.data
            Glide.with(this)
                .load(selectedImageUri)
                .circleCrop()
                .into(binding.profileImage)
            binding.saveButton.visibility = View.VISIBLE
        }
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        imagePickerLauncher.launch(intent)
    }

    private fun saveChanges() {
        val user = auth.currentUser ?: return
        val newName = binding.nameField.text.toString().trim()

        if (newName.isEmpty()) {
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(newName)
            
        if (selectedImageUri != null) {
            profileUpdates.setPhotoUri(selectedImageUri)
        }

        binding.saveButton.isEnabled = false
        user.updateProfile(profileUpdates.build())
            .addOnCompleteListener { task ->
                binding.saveButton.isEnabled = true
                if (task.isSuccessful) {
                    Toast.makeText(this, "Profile Updated!", Toast.LENGTH_SHORT).show()
                    toggleEditing(false)
                } else {
                    Toast.makeText(this, "Update Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
