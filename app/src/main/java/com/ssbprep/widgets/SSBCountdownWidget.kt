package com.ssbprep.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.ssbprep.R
import com.ssbprep.activities.HomeActivity
import com.ssbprep.utils.SSBCountdownManager

class SSBCountdownWidget : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val views = RemoteViews(context.packageName, R.layout.layout_ssb_countdown_widget)
        
        val timestamp = SSBCountdownManager.getSSBDate(context)
        val name = SSBCountdownManager.getSSBName(context) ?: "SSB"
        val days = SSBCountdownManager.getDaysRemaining(timestamp)

        views.setTextViewText(R.id.widgetSsbName, name)
        views.setTextViewText(R.id.widgetDaysText, if (days >= 0) days.toString() else "0")

        val intent = Intent(context, HomeActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        views.setOnClickPendingIntent(R.id.widgetDaysText, pendingIntent)

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}
