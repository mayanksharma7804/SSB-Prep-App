package com.ssbprep.activities

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.ssbprep.R
import com.ssbprep.utils.SSBCountdownManager
import com.ssbprep.widgets.SSBCountdownWidget
import java.text.SimpleDateFormat
import java.util.*

class SSBCountdownDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ssb_countdown_details)

        val backButton: ImageButton = findViewById(R.id.backButton)
        val ssbNameText: TextView = findViewById(R.id.detailSsbName)
        val ssbDateText: TextView = findViewById(R.id.detailSsbDate)
        val daysRemainingText: TextView = findViewById(R.id.detailDaysRemaining)
        val pinWidgetButton: MaterialButton = findViewById(R.id.pinWidgetButton)
        val removeButton: MaterialButton = findViewById(R.id.removeCountdownButton)

        val name = SSBCountdownManager.getSSBName(this) ?: "SSB Interview"
        val timestamp = SSBCountdownManager.getSSBDate(this)
        val days = SSBCountdownManager.getDaysRemaining(timestamp)

        ssbNameText.text = name
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        ssbDateText.text = "Reporting Date: ${sdf.format(Date(timestamp))}"
        daysRemainingText.text = if (days >= 0) days.toString() else "0"

        backButton.setOnClickListener { finish() }

        removeButton.setOnClickListener {
            SSBCountdownManager.clearSSBDate(this)
            updateWidgets()
            Toast.makeText(this, "Countdown Removed", Toast.LENGTH_SHORT).show()
            finish()
        }

        pinWidgetButton.setOnClickListener {
            pinWidget()
        }
    }

    private fun pinWidget() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val appWidgetManager = getSystemService(AppWidgetManager::class.java)
            val myProvider = ComponentName(this, SSBCountdownWidget::class.java)

            if (appWidgetManager.isRequestPinAppWidgetSupported) {
                appWidgetManager.requestPinAppWidget(myProvider, null, null)
            } else {
                Toast.makeText(this, "Pinning not supported on your launcher", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Manual widget addition required for your Android version", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateWidgets() {
        val intent = Intent(this, SSBCountdownWidget::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        val ids = AppWidgetManager.getInstance(application).getAppWidgetIds(ComponentName(application, SSBCountdownWidget::class.java))
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        sendBroadcast(intent)
    }
}