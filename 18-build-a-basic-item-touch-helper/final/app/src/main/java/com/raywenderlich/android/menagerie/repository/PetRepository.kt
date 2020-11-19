package com.raywenderlich.android.menagerie.repository

import androidx.lifecycle.LiveData
import com.raywenderlich.android.menagerie.data.model.Pet

interface PetRepository {

  fun getPets(): LiveData<List<Pet>>

  suspend fun getPetData(): List<Pet>

  fun getSleepingPets(): LiveData<List<Pet>>

  suspend fun updatePet(pet: Pet)

  suspend fun insertPets(pets: List<Pet>)

  suspend fun updatePets(pets: List<Pet>)

  fun getPetDetails(petId: String): LiveData<Pet>
}