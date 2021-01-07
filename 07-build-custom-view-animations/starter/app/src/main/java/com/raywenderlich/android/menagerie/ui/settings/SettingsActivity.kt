package com.raywenderlich.android.menagerie.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.raywenderlich.android.menagerie.databinding.ActivitySettingsBinding
import com.raywenderlich.android.menagerie.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity(), SettingsView {

  private val binding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }
  private val settingsViewModel by viewModels<SettingsViewModel>()

  companion object {
    fun getIntent(context: Context) = Intent(context, SettingsActivity::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    settingsViewModel.setView(this)
    setContentView(binding.root)
    setupUi()
    // Todo enter animation
  }

  private fun setupUi() {
    binding.settingsButton.setOnClickListener { exitCircular() }
    binding.sleepAllPets.setOnClickListener { settingsViewModel.putAllPetsToBed() }
    binding.wakeAllPets.setOnClickListener { settingsViewModel.wakeUpAllPets() }
    binding.logOut.setOnClickListener { settingsViewModel.logOut() }

    settingsViewModel.sleepingPets.observe(this, { sleepingPets ->
      if (sleepingPets != null && sleepingPets.isNotEmpty()) {
        val allPetsSleeping = sleepingPets.all { it.isSleeping }
        val isAnyPetAsleep = sleepingPets.any { it.isSleeping }

        binding.sleepAllPets.isEnabled = !allPetsSleeping
        binding.wakeAllPets.isEnabled = isAnyPetAsleep
      } else {
        binding.sleepAllPets.isEnabled = true
        binding.wakeAllPets.isEnabled = false
      }
    })
  }

  override fun onUserLoggedOut() {
    // TODO transition
    startActivity(LoginActivity.getIntent(this))
  }

  override fun onBackPressed() = exitCircular()

  private fun exitCircular() {
    // TODO animation
    finish()
  }
}