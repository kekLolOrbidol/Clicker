package com.arkadiusz.surma.cookieclicker

import android.app.Application
import com.arkadiusz.surma.cookieclicker.infrastructure.Logger
import com.arkadiusz.surma.cookieclicker.infrastructure.memory.inMemoryGameRepositoryModule
import com.arkadiusz.surma.cookieclicker.infrastructure.memory.inMemoryGameStoreModule
import com.onesignal.OneSignal
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*

class CookieClickerApplication: Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        import(androidXModule(this@CookieClickerApplication))

        bind() from instance(Logger())

        import(inMemoryGameRepositoryModule)
        import(inMemoryGameStoreModule)
    }

    override fun onCreate() {
        super.onCreate()
        val k = kodein
        println(k)
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.startInit(this)
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .unsubscribeWhenNotificationsAreDisabled(true)
            .init();
    }
}
