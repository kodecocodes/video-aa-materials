package com.raywenderlich.android.menagerie.ui.settings

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raywenderlich.android.menagerie.preferences.MenageriePreferences
import com.raywenderlich.android.menagerie.repository.PetRepository
import kotlinx.coroutines.launch

class SettingsViewModel @ViewModelInject constructor(
  private val menageriePreferences: MenageriePreferences,
  private val repository: PetRepository
) : ViewModel() {

  private lateinit var view: SettingsView

  val sleepingPets by lazy { repository.getSleepingPets() }

  fun setView(view: SettingsView) {
    this.view = view
  }

  fun logOut() {
    menageriePreferences.setUserLoggedIn(false)
    view.onUserLoggedOut()
  }

  fun wakeUpAllPets() {
    viewModelScope.launch {
      val pets = repository.getPetData()

      repository.updatePets(pets.map { pet -> pet.copy(isSleeping = false) })
    }
  }

  fun putAllPetsToBed() {
    viewModelScope.launch {
      val pets = repository.getPetData()

      repository.updatePets(pets.map { pet -> pet.copy(isSleeping = true) })
    }
  }
}