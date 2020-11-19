package com.raywenderlich.android.menagerie.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.raywenderlich.android.menagerie.data.model.Pet

@Database(version = 1, entities = [Pet::class])
abstract class MenagerieDatabase : RoomDatabase() {

  companion object {
    private const val DATABASE_NAME = "menagerie"

    fun buildDatabase(context: Context): MenagerieDatabase {
      return Room
        .databaseBuilder(context, MenagerieDatabase::class.java, DATABASE_NAME)
        .build()
    }
  }

  abstract fun petDao(): PetDao
}