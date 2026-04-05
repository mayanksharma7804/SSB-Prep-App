package com.ssbprep

import android.app.Application
import android.content.Context
import com.ssbprep.utils.LocaleHelper
import com.ssbprep.utils.ThemeManager

class SSBPrepApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ThemeManager.applyTheme(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base))
    }
}
