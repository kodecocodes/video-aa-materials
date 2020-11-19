package com.raywenderlich.android.menagerie.preferences

import android.content.SharedPreferences
import javax.inject.Inject

class MenageriePreferencesImpl @Inject constructor(
  private val sharedPreferences: SharedPreferences
) : MenageriePreferences {

  companion object {
    private const val KEY_IS_LOGGED_IN = "isLoggedIn"
  }

  override fun isUserLoggedIn(): Boolean = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)

  override fun setUserLoggedIn(isLoggedIn: Boolean) {
    sharedPreferences
      .edit()
      .putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
      .apply()
  }
}