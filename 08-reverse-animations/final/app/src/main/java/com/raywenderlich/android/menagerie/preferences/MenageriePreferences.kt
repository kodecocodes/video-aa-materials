package com.raywenderlich.android.menagerie.preferences

interface MenageriePreferences {

  fun isUserLoggedIn(): Boolean

  fun setUserLoggedIn(isLoggedIn: Boolean)
}