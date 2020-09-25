package com.arkadiusz.surma.cookieclicker.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import java.util.*


class CustomNotification {
    fun scheduleMsg(context: Context) {
        val calendar: Calendar = Calendar.getInstance()
        var n = 0
//        for (i in 0..29) {
//            calendar.setTimeInMillis(System.currentTimeMillis())
//            for (j in 0..6){
//                calendar.add(Calendar.DAY_OF_MONTH, i)
//                calendar.set(Calendar.HOUR_OF_DAY, 10 + j * 2)
//                calendar.set(Calendar.MINUTE, 0)
//                scheduleMessage(calendar, context, i)
//            }
//            //scheduleMessage(calendar, context, i)
//        }
        calendar.setTimeInMillis(System.currentTimeMillis())
        scheduleMessage(calendar, context, 0)
    }

    private fun scheduleMessage(calendar: Calendar, context: Context, type: Int) {
        val i = Intent(context, BroadCast::class.java)
        i.putExtra(TYPE_EXTRA, type)
        val pendingIntent = PendingIntent.getBroadcast(context, type, i, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManagerRTC = context.getSystemService(ALARM_SERVICE) as AlarmManager
        val interval = 10
        alarmManagerRTC.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + interval,
            interval.toLong(), pendingIntent)
//        alarmManagerRTC[AlarmManager.RTC_WAKEUP, calendar.timeInMillis] = pendingIntent
    }

    fun getNotificationManager(context: Context): Any? {
        return context.getSystemService(Context.NOTIFICATION_SERVICE)
    }

    companion object {
        const val TYPE_EXTRA = "type"
    }
}