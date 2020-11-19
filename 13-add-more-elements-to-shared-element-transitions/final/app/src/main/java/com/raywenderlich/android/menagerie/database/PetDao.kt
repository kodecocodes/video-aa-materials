package com.raywenderlich.android.menagerie.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.raywenderlich.android.menagerie.data.model.Pet

@Dao
interface PetDao {

  @Query("SELECT * FROM pet WHERE isSleeping = 1")
  fun getSleepingPets(): LiveData<List<Pet>>

  @Query("SELECT * FROM pet")
  fun getPets(): LiveData<List<Pet>>

  @Query("SELECT * FROM pet")
  suspend fun getPetData(): List<Pet>

  @Update(onConflict = OnConflictStrategy.REPLACE)
  suspend fun updatePet(pet: Pet)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertPets(pets: List<Pet>)

  @Update(onConflict = OnConflictStrategy.REPLACE)
  suspend fun updatePets(pets: List<Pet>)

  @Query("SELECT * FROM pet WHERE id = :petId")
  fun getPetDetails(petId: String): LiveData<Pet>
}