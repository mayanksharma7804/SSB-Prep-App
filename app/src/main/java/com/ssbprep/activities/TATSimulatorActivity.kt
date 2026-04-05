package com.ssbprep.activities

import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.ssbprep.R
import java.util.*

class TATSimulatorActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var blackOverlay: View
    private lateinit var timerText: TextView
    private lateinit var phaseLabel: TextView
    private lateinit var imageCountText: TextView

    private var imageIds = mutableListOf<Int>()
    private var currentImageIndex = 0
    private var isObserving = true
    private var countDownTimer: CountDownTimer? = null
    private val toneGenerator = ToneGenerator(AudioManager.STREAM_ALARM, 100)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Keep screen on and Prevent Screenshots
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        
        setContentView(R.layout.activity_tat_simulator)

        imageView = findViewById(R.id.simulatorImage)
        blackOverlay = findViewById(R.id.blackOverlay)
        timerText = findViewById(R.id.timerText)
        phaseLabel = findViewById(R.id.phaseLabel)
        imageCountText = findViewById(R.id.imageCountText)

        val setId = intent.getIntExtra("SET_ID", 1)
        prepareData(setId)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Back disabled during test
            }
        })

        startTest()
    }

    private fun prepareData(setId: Int) {
        val setPrefix = "set$setId"
        val tempImages = mutableListOf<Int>()
        
        // Load the 11 images for the set
        for (i in 1..11) {
            val resId = resources.getIdentifier("${setPrefix}_img$i", "drawable", packageName)
            if (resId != 0) {
                tempImages.add(resId)
            }
        }
        
        // Shuffle the 11 images
        tempImages.shuffle()
        
        // Add shuffled images to the main list
        imageIds.addAll(tempImages)
        
        // Add the 12th image as the blank slide (never shuffled, always last)
        imageIds.add(R.drawable.blank_slide)
    }

    private fun startTest() {
        currentImageIndex = 0
        isObserving = true
        runPhase()
    }

    private fun runPhase() {
        if (currentImageIndex >= imageIds.size) {
            finish()
            return
        }

        toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP)

        if (isObserving) {
            phaseLabel.text = "OBSERVE IMAGE"
            blackOverlay.visibility = View.GONE
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(imageIds[currentImageIndex])
            imageCountText.text = "Image ${currentImageIndex + 1}/${imageIds.size}"
            
            startTimer(30000) {
                isObserving = false
                runPhase()
            }
        } else {
            phaseLabel.text = "WRITE STORY IN NOTEBOOK"
            blackOverlay.visibility = View.VISIBLE
            imageView.visibility = View.GONE
            
            startTimer(240000) {
                isObserving = true
                currentImageIndex++
                runPhase()
            }
        }
    }

    private fun startTimer(millis: Long, onFinished: () -> Unit) {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(millis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = (millisUntilFinished / 1000) % 60
                val minutes = (millisUntilFinished / 1000) / 60
                timerText.text = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                onFinished()
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
        toneGenerator.release()
    }
}
