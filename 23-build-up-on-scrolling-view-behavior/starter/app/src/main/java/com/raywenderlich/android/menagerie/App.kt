package com.raywenderlich.android.menagerie

import android.app.Application
import com.raywenderlich.android.menagerie.data.petsData
import com.raywenderlich.android.menagerie.repository.PetRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

  @Inject
  lateinit var petRepository: PetRepository

  override fun onCreate() {
    super.onCreate()
    prePopulateDatabaseIfEmpty()
  }

  private fun prePopulateDatabaseIfEmpty() {
    GlobalScope.launch {
      val data = petRepository.getPetData()

      if (data.isEmpty()) {
        petRepository.insertPets(petsData)
      }
    }
  }
}