package com.raywenderlich.android.menagerie.ui.main

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Animatable2
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raywenderlich.android.menagerie.databinding.ActivityMainBinding
import com.raywenderlich.android.menagerie.ui.settings.SettingsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  companion object {
    fun getIntent(context: Context): Intent {
      val intent = Intent(context, MainActivity::class.java)
      intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
      return intent
    }
  }

  private val mainPagerAdapter by lazy { MainPagerAdapter(this, supportFragmentManager) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setBinding()
  }

  private fun setBinding() {
    val binding = ActivityMainBinding.inflate(layoutInflater)

    setContentView(binding.root)
    setupUi(binding)
  }

  private fun setupUi(binding: ActivityMainBinding) {
    val viewPager = binding.mainPager
    val tabs = binding.tabs
    val settingsButton = binding.settingsButton

    viewPager.adapter = mainPagerAdapter
    tabs.setupWithViewPager(viewPager)

    settingsButton.setOnClickListener {
      val animatable = binding.settingsButton.drawable as? Animatable2

      if (animatable != null) {
        animatable.registerAnimationCallback(object : Animatable2.AnimationCallback() {
          override fun onAnimationEnd(drawable: Drawable?) {
            showSettings()
            super.onAnimationEnd(drawable)
          }
        })

        animatable.start()
      } else {
        showSettings()
      }
    }
  }

  private fun showSettings() {
    startActivity(SettingsActivity.getIntent(this))
  }
}