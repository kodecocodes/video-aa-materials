package com.raywenderlich.android.menagerie.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.raywenderlich.android.menagerie.data.model.Pet
import com.raywenderlich.android.menagerie.database.PetDao
import javax.inject.Inject

class PetRepositoryImpl @Inject constructor(
  private val petDao: PetDao
) : PetRepository {

  override fun getPets(): LiveData<List<Pet>> = petDao.getPets().map { pets ->
    pets.sortedBy { it.name }
  }

  override suspend fun getPetData(): List<Pet> = petDao.getPetData()

  override fun getSleepingPets(): LiveData<List<Pet>> = petDao.getSleepingPets().map { pets ->
    pets.sortedBy { it.name }
  }

  override suspend fun updatePet(pet: Pet) = petDao.updatePet(pet)
  override suspend fun insertPets(pets: List<Pet>) = petDao.insertPets(pets)
  override suspend fun updatePets(pets: List<Pet>) = petDao.updatePets(pets)

  override fun getPetDetails(petId: String): LiveData<Pet> = petDao.getPetDetails(petId)
}