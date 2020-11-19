package com.raywenderlich.android.menagerie.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Pet(
  @PrimaryKey val id: String,
  val name: String,
  val description: String,
  val image: Int,
  val isSleeping: Boolean = false
) : Serializable