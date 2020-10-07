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

package com.raywenderlich.android.foodmart.ui

import com.raywenderlich.android.foodmart.model.Cart
import com.raywenderlich.android.foodmart.model.Favorites
import com.raywenderlich.android.foodmart.model.FoodRepository
import com.raywenderlich.android.foodmart.ui.cart.CartContract
import com.raywenderlich.android.foodmart.ui.cart.CartPresenter
import com.raywenderlich.android.foodmart.ui.categories.CategoriesContract
import com.raywenderlich.android.foodmart.ui.categories.CategoriesPresenter
import com.raywenderlich.android.foodmart.ui.categories.CategoryContract
import com.raywenderlich.android.foodmart.ui.categories.CategoryPresenter
import com.raywenderlich.android.foodmart.ui.detail.FoodContract
import com.raywenderlich.android.foodmart.ui.detail.FoodPresenter
import com.raywenderlich.android.foodmart.ui.items.ItemsContract
import com.raywenderlich.android.foodmart.ui.items.ItemsPresenter


object Injection {

  fun provideFoodRepository(): FoodRepository = FoodRepository

  fun provideCart(): Cart = Cart

  fun provideFavorites(): Favorites = Favorites

  fun provideItemsPresenter(itemsView: ItemsContract.View): ItemsContract.Presenter =
      ItemsPresenter(provideFoodRepository(), provideCart(), provideFavorites(), itemsView)

  fun provideFoodPresenter(foodView: FoodContract.View): FoodContract.Presenter =
      FoodPresenter(provideFoodRepository(), provideCart(), foodView)

  fun provideCartPresenter(cartView: CartContract.View): CartContract.Presenter =
      CartPresenter(provideCart(), cartView)

  fun provideCategoriesPresenter(view: CategoriesContract.View): CategoriesContract.Presenter =
      CategoriesPresenter(provideFoodRepository(), view)

  fun provideCategoryPresenter(itemsView: CategoryContract.View): CategoryContract.Presenter =
      CategoryPresenter(provideFoodRepository(), itemsView)
}