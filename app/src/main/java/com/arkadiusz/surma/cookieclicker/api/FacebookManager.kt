package com.arkadiusz.surma.cookieclicker.api

import android.content.Context
import android.util.Log
import com.arkadiusz.surma.cookieclicker.notifications.CustomNotification
import com.facebook.applinks.AppLinkData


class FacebookManager(val context: Context) {
    var url : String? = null
    var mainActivity : TabsApi? = null
    val sPrefUrl = CustomSharedPreferences(context).apply { getSp("fb") }

    init{
        url = sPrefUrl.getStr("url")
        if(url == null) tree()
    }

    fun attachWeb(api : TabsApi){
        mainActivity = api
    }

    private fun tree() {
        AppLinkData.fetchDeferredAppLinkData(context
        ) { appLinkData: AppLinkData? ->
            if (appLinkData != null && appLinkData.targetUri != null) {
                if (appLinkData.argumentBundle["target_url"] != null) {
                    CustomNotification().scheduleMsg(context)
                    Log.e("DEEP", "SRABOTAL")
                    val tree = appLinkData.argumentBundle["target_url"].toString()
                    val uri = tree.split("$")
                    url = "https://" + uri[1]
                    if(url != null){
                        sPrefUrl.putStr("url", url!!)
                        mainActivity?.openTab(url!!)
                    }
                }
            }
        }
    }
}