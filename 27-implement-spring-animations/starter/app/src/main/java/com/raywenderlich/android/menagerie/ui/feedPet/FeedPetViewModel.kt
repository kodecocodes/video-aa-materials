package com.raywenderlich.android.menagerie.ui.feedPet

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.raywenderlich.android.menagerie.data.model.Pet
import com.raywenderlich.android.menagerie.repository.PetRepository

class FeedPetViewModel @ViewModelInject constructor(
  private val repository: PetRepository
) : ViewModel() {

  private lateinit var view: FeedPetView
  lateinit var petData: LiveData<Pet>
    private set

  fun setView(view: FeedPetView) {
    this.view = view
  }

  fun setPet(data: Pet) {
    petData = repository.getPetDetails(data.id)
  }
}
