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
import com.raywenderlich.android.foodmart.model.events.CartDeleteItemEvent
import com.raywenderlich.android.foodmart.model.events.CartEvent
import org.greenrobot.eventbus.EventBus


object Cart {

  private val KEY_CART = "KEY_CART"
  private val gson = Gson()

  private var itemIds: MutableList<Int>? = null

  fun isInCart(item: Food): Boolean {
    return getCartItemIds()?.contains(item.id) == true
  }

  fun addItem(item: Food) {
    val itemIds = getCartItemIds()
    itemIds?.let {
      item.isInCart = true
      itemIds.add(item.id)
      saveCart(KEY_CART, itemIds)
    }
  }

  fun removeItem(item: Food) {
    val itemIds = getCartItemIds()
    itemIds?.let {
      item.isInCart = false
      val position = itemIds.indexOf(item.id)
      itemIds.remove(item.id)
      saveCart(KEY_CART, itemIds)
      EventBus.getDefault().post(CartDeleteItemEvent(position))
    }
  }

  private fun saveCart(key: String, list: List<Int>) {
    val json = gson.toJson(list)
    sharedPrefs().edit().putString(key, json).apply()
    EventBus.getDefault().post(CartEvent())
  }

  private fun getCartItemIds(): MutableList<Int>? {
    if (itemIds == null) {
      val json = sharedPrefs().getString(KEY_CART, "")
      val type = object : TypeToken<MutableList<Int>>() {}.type
      itemIds = gson.fromJson<MutableList<Int>>(json, type) ?: return mutableListOf()
    }
    return itemIds
  }

  fun getCartItems(): List<Food> {
    val itemIds = getCartItemIds()
    val items = mutableListOf<Food>()
    itemIds?.let {
      itemIds.forEach {
        val item = FoodRepository.getFoodById(it)
        item?.let {
          items.add(item)
        }
      }
    }
    return items
  }

  fun cartSize(): Int {
    val itemIds = getCartItemIds()
    itemIds?.let {
      return itemIds.size
    }
    return 0
  }

  fun addAllToCart() {
    val itemIds = getCartItemIds()
    itemIds?.let {
      val foods = FoodRepository.getFoods()
      foods.forEach { food ->
        if (!food.isInCart) {
          addItem(food)
        }
      }
    }
  }

  fun clearCart() {
    val itemIds = getCartItemIds()
    itemIds?.let {
      itemIds.clear()
      val foods = FoodRepository.getFoods()
      foods.forEach { it.isInCart = false }
      saveCart(KEY_CART, itemIds)
    }
  }

  private fun sharedPrefs() = PreferenceManager.getDefaultSharedPreferences(FoodMartApplication.getAppContext())
}