package com.ssbprep.activities

import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.ssbprep.R
import com.ssbprep.data.WATData
import java.util.*

class WATSimulatorActivity : AppCompatActivity() {

    private lateinit var wordText: TextView
    private lateinit var wordCounter: TextView
    private lateinit var progressBar: LinearProgressIndicator

    private var words = mutableListOf<String>()
    private var currentWordIndex = 0
    private var countDownTimer: CountDownTimer? = null
    private val toneGenerator = ToneGenerator(AudioManager.STREAM_ALARM, 100)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Keep screen on and Prevent Screenshots
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)

        setContentView(R.layout.activity_wat_simulator)

        wordText = findViewById(R.id.watWordText)
        wordCounter = findViewById(R.id.wordCounter)
        progressBar = findViewById(R.id.wordProgressBar)

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
        val setIndex = (setId - 1).coerceIn(0, WATData.allSets.size - 1)
        words.addAll(WATData.allSets[setIndex])
        words.shuffle()
    }

    private fun startTest() {
        currentWordIndex = 0
        showNextWord()
    }

    private fun showNextWord() {
        if (currentWordIndex >= words.size) {
            finish()
            return
        }

        toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP)
        wordText.text = words[currentWordIndex]
        wordCounter.text = "Word ${currentWordIndex + 1} / ${words.size}"
        
        startTimer()
    }

    private fun startTimer() {
        countDownTimer?.cancel()
        
        val duration = 15000L
        progressBar.max = 100
        progressBar.progress = 100

        countDownTimer = object : CountDownTimer(duration, 20) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = (millisUntilFinished.toFloat() / duration.toFloat() * 100).toInt()
                progressBar.progress = progress
            }

            override fun onFinish() {
                progressBar.progress = 0
                currentWordIndex++
                showNextWord()
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
        toneGenerator.release()
    }
}