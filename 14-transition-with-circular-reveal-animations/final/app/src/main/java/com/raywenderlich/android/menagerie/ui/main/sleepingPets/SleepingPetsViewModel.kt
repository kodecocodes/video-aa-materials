package com.raywenderlich.android.menagerie.ui.main.sleepingPets

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raywenderlich.android.menagerie.data.model.Pet
import com.raywenderlich.android.menagerie.repository.PetRepository
import kotlinx.coroutines.launch

class SleepingPetsViewModel @ViewModelInject constructor(
  private val repository: PetRepository
) : ViewModel() {

  private lateinit var view: SleepingPetsView

  val sleepingPets by lazy { repository.getSleepingPets() }

  fun setView(view: SleepingPetsView) {
    this.view = view
  }

  fun onPetSleepClick(pet: Pet) {
    viewModelScope.launch {
      repository.updatePet(pet.copy(isSleeping = !pet.isSleeping))
    }
  }
}