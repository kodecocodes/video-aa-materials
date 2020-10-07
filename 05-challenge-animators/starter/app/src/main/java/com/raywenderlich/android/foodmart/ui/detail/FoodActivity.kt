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

package com.raywenderlich.android.foodmart.ui.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.raywenderlich.android.foodmart.R
import com.raywenderlich.android.foodmart.app.toast
import com.raywenderlich.android.foodmart.model.events.CartEvent
import com.raywenderlich.android.foodmart.ui.Injection
import kotlinx.android.synthetic.main.activity_food.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class FoodActivity : AppCompatActivity(), FoodContract.View {

  override lateinit var presenter: FoodContract.Presenter

  companion object {
    private const val EXTRA_FOOD_ID = "place_id"

    fun newIntent(context: Context, foodId: Int): Intent {
      val intent = Intent(context, FoodActivity::class.java)
      intent.putExtra(EXTRA_FOOD_ID, foodId)
      return intent
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_food)

    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    presenter = Injection.provideFoodPresenter(this)

    val food = presenter.getFood(intent.extras.getInt(EXTRA_FOOD_ID))

    food?.let {
      foodImage.setImageResource(resources.getIdentifier(food.largeImage, null, packageName))
      collapsingToolbar.title = food.name
      collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent))
      foodName.text = food.name
      foodDescription.text = food.description
      fab.setImageResource(if (food.isInCart) R.drawable.ic_done else R.drawable.ic_add)
      moreInfo.setOnClickListener {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(food.link))
        startActivity(browserIntent)
      }
      fab.setOnClickListener {
        if (food.isInCart) {
          presenter.removeItem(food)
        } else {
          presenter.addItem(food)
        }
      }
    }
  }

  override fun onResume() {
    super.onResume()
    EventBus.getDefault().register(this)
  }

  override fun onPause() {
    super.onPause()
    EventBus.getDefault().unregister(this)
  }

  @Suppress("UNUSED_PARAMETER")
  @Subscribe(threadMode = ThreadMode.MAIN)
  fun onCartEvent(event: CartEvent) {
    val food = presenter.getFood(intent.extras.getInt(EXTRA_FOOD_ID))
    val isInCart = food?.isInCart ?: false
    fab.setImageResource(if (isInCart) R.drawable.ic_done else R.drawable.ic_add)
    toast(if (isInCart) getString(R.string.added_to_cart) else getString(R.string.removed_from_cart))
  }
}
