package com.arkadiusz.surma.cookieclicker.notifications

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build

import androidx.core.app.NotificationCompat;
import com.arkadiusz.surma.cookieclicker.R
import com.arkadiusz.surma.cookieclicker.activity.MenuActivity


class BroadCast : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val type = intent.getIntExtra(CustomNotification.TYPE_EXTRA, 0)
        val intentToRepeat = Intent(context, MenuActivity::class.java)
        intentToRepeat.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pendingIntent = PendingIntent.getActivity(context, type, intentToRepeat, PendingIntent.FLAG_UPDATE_CURRENT)
        val nm = com.arkadiusz.surma.cookieclicker.notifications.CustomNotification().getNotificationManager(context)
        val notification: Notification = configNotification(context, pendingIntent, nm as NotificationManager?).build()
        nm?.notify(type, notification)
    }

    fun configNotification(context: Context, pendingIntent: PendingIntent?, nm: NotificationManager?): NotificationCompat.Builder {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("default",
                    "Daily Notification",
                    NotificationManager.IMPORTANCE_HIGH)
            channel.description = "Daily Notification"
            nm?.createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(context, "default")
                .setContentIntent(pendingIntent)
                .setContentTitle("kek")
                .setSmallIcon(R.drawable.symbol)
                .setAutoCancel(true)
    }
}
