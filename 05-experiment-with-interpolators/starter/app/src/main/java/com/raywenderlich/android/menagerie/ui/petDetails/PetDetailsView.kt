package com.raywenderlich.android.menagerie.ui.petDetails

import com.raywenderlich.android.menagerie.data.model.Pet

interface PetDetailsView {

  fun showFeedingSleepingPetMessage()

  fun feedPet(pet: Pet)
}