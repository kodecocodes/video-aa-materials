package com.raywenderlich.android.menagerie.di

import com.raywenderlich.android.menagerie.preferences.MenageriePreferences
import com.raywenderlich.android.menagerie.preferences.MenageriePreferencesImpl
import com.raywenderlich.android.menagerie.repository.PetRepository
import com.raywenderlich.android.menagerie.repository.PetRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
abstract class DataModule {

  @Binds
  abstract fun repository(petRepositoryImpl: PetRepositoryImpl): PetRepository

  @Binds
  abstract fun preferences(menageriePreferencesImpl: MenageriePreferencesImpl): MenageriePreferences
}