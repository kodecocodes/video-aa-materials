/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package com.raywenderlich.android.foodmart.model

import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raywenderlich.android.foodmart.app.FoodMartApplication


object Favorites {

  private val KEY_FAVORITES = "KEY_FAVORITES"
  private val gson = Gson()

  private var favorites: MutableList<Int>? = null

  fun isFavorite(food: Food): Boolean = getFavorites()?.contains(food.id) == true

  fun addFavorite(food: Food) {
    val favorites = getFavorites()
    favorites?.let {
      food.isFavorite = true
      favorites.add(food.id)
      saveFavorites(KEY_FAVORITES, favorites)
    }
  }

  fun removeFavorite(food: Food) {
    val favorites = getFavorites()
    favorites?.let {
      food.isFavorite = false
      favorites.remove(food.id)
      saveFavorites(KEY_FAVORITES, favorites)
    }
  }

  fun getFavorites(): MutableList<Int>? {
    if (favorites == null) {
      val json = sharedPrefs().getString(KEY_FAVORITES, "")
      val type = object : TypeToken<MutableList<Int>>() {}.type
      favorites = gson.fromJson<MutableList<Int>>(json, type) ?: return mutableListOf()
    }
    return favorites
  }

  fun saveFavorites(list: List<Int>) {
    saveFavorites(KEY_FAVORITES, list)
  }

  private fun saveFavorites(key: String, list: List<Int>) {
    val json = gson.toJson(list)
    sharedPrefs().edit().putString(key, json).apply()
  }

  private fun sharedPrefs() = PreferenceManager.getDefaultSharedPreferences(FoodMartApplication.getAppContext())
}