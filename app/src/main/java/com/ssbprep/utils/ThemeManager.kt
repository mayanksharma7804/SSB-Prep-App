package com.ssbprep.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import kotlin.math.hypot

object ThemeManager {
    private const val PREFS_NAME = "theme_prefs"
    private const val KEY_IS_DARK_MODE = "is_dark_mode"

    private var pendingTransitionBitmap: Bitmap? = null
    private var pendingTransitionX: Int = 0
    private var pendingTransitionY: Int = 0

    fun applyTheme(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isDarkMode = prefs.getBoolean(KEY_IS_DARK_MODE, true)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    fun toggleTheme(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isDarkMode = prefs.getBoolean(KEY_IS_DARK_MODE, true)
        prefs.edit().putBoolean(KEY_IS_DARK_MODE, !isDarkMode).apply()
        applyTheme(context)
    }

    fun toggleThemeWithAnimation(activity: Activity, view: View) {
        val root = activity.window.decorView
        val bitmap = Bitmap.createBitmap(root.width, root.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        root.draw(canvas)

        val location = IntArray(2)
        view.getLocationInWindow(location)
        
        pendingTransitionBitmap = bitmap
        pendingTransitionX = location[0] + view.width / 2
        pendingTransitionY = location[1] + view.height / 2

        toggleTheme(activity)
        activity.recreate()
    }

    fun checkAndAnimate(activity: Activity) {
        val bitmap = pendingTransitionBitmap ?: return
        val cx = pendingTransitionX
        val cy = pendingTransitionY
        pendingTransitionBitmap = null

        val root = activity.findViewById<ViewGroup>(android.R.id.content)
        if (root.childCount == 0) return
        
        // The current activity content (with the new theme)
        val content = root.getChildAt(0)
        
        // Hide the new content immediately to prevent a flash of the new theme
        content.visibility = View.INVISIBLE

        // Create an overlay with the old theme screenshot
        val imageView = ImageView(activity)
        imageView.setImageBitmap(bitmap)
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        root.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        imageView.post {
            val finalRadius = hypot(root.width.toDouble(), root.height.toDouble()).toFloat()
            
            // Bring the new content to the front so it reveals OVER the old screenshot
            content.bringToFront()
            content.visibility = View.VISIBLE
            
            // Animate the reveal from the icon center to the edges of the screen
            val anim = ViewAnimationUtils.createCircularReveal(content, cx, cy, 0f, finalRadius)
            anim.duration = 600 // Faster and smoother transition
            anim.interpolator = DecelerateInterpolator()
            
            anim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    // Clean up the screenshot overlay
                    root.removeView(imageView)
                }
            })
            anim.start()
        }
    }

    fun isDarkMode(context: Context): Boolean {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getBoolean(KEY_IS_DARK_MODE, true)
    }
}
