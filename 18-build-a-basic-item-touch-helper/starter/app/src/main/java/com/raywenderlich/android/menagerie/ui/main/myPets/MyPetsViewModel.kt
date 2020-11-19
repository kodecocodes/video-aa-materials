package com.raywenderlich.android.menagerie.ui.main.myPets

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raywenderlich.android.menagerie.data.model.Pet
import com.raywenderlich.android.menagerie.repository.PetRepository
import kotlinx.coroutines.launch

class MyPetsViewModel @ViewModelInject constructor(
  private val repository: PetRepository
) : ViewModel() {

  private lateinit var view: MyPetsView

  val myPets by lazy { repository.getPets() }

  fun setView(view: MyPetsView) {
    this.view = view
  }

  fun onPetSleepClick(pet: Pet) {
    viewModelScope.launch {
      repository.updatePet(pet.copy(isSleeping = !pet.isSleeping))
    }
  }
}