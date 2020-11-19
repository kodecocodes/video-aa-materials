package com.raywenderlich.android.menagerie.ui.petDetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raywenderlich.android.menagerie.data.model.Pet
import com.raywenderlich.android.menagerie.repository.PetRepository
import kotlinx.coroutines.launch

class PetDetailsViewModel @ViewModelInject constructor(
  private val repository: PetRepository
) : ViewModel() {

  private lateinit var view: PetDetailsView
  lateinit var petData: LiveData<Pet>
    private set

  fun setView(view: PetDetailsView) {
    this.view = view
  }

  fun setPet(data: Pet) {
    val petId = data.id

    petData = repository.getPetDetails(petId)
  }

  fun onPetSleepTap(pet: Pet) {
    viewModelScope.launch {
      repository.updatePet(pet.copy(isSleeping = !pet.isSleeping))
    }
  }

  fun onPetFeedTap(pet: Pet) {
    if (pet.isSleeping) {
      view.showFeedingSleepingPetMessage()
    } else {
      view.feedPet(pet)
    }
  }
}