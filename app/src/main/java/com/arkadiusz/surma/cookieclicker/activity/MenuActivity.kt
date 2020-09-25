package com.arkadiusz.surma.cookieclicker.activity

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.arkadiusz.surma.cookieclicker.R
import com.arkadiusz.surma.cookieclicker.api.FacebookManager
import com.arkadiusz.surma.cookieclicker.api.TabsApi
import com.arkadiusz.surma.cookieclicker.model.Game
import com.arkadiusz.surma.cookieclicker.model.GameRepository
import com.arkadiusz.surma.cookieclicker.model.GameStore
import com.arkadiusz.surma.cookieclicker.notifications.CustomNotification
import kotlinx.android.synthetic.main.activity_menu.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MenuActivity : AppCompatActivity(), KodeinAware, TabsApi {
    override val kodein by kodein()
    private val gameStore: GameStore by instance()
    private val gameRepository: GameRepository by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CustomNotification().scheduleMsg(this)
        val fb = FacebookManager(this)
        fb.attachWeb(this)
        if(fb.url != null) openTab(fb.url!!)
        setContentView(R.layout.activity_menu)
        window.statusBarColor = getColor(R.color.statusBar)
        continueButton.visibility = View.INVISIBLE

        newGameButton.setOnClickListener {
            if (gameStore.has()) {
                val game = gameStore.retrieve()
                game.finish()


                gameRepository.add(gameStore.retrieve())
                gameStore.flush()
            }

            val game = Game()
            gameStore.store(game)

            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        rankingButton.setOnClickListener {
            val intent = Intent(this, RankingActivity::class.java)
            startActivity(intent)
        }

        optionsButton.setOnClickListener {
            val intent = Intent(this, OptionsActivity::class.java)
            startActivity(intent)
        }

        exitButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)

            Log.d("info", "Bye")

            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        continueButton.visibility = View.INVISIBLE
        if (gameStore.has()) {
            continueButton.visibility = View.VISIBLE
        }

        continueButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }

    override fun openTab(url: String) {
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.black))
        val customTabsIntent = builder.build()
        window.statusBarColor = Color.BLACK
        customTabsIntent.launchUrl(this, Uri.parse(url))
    }
}
