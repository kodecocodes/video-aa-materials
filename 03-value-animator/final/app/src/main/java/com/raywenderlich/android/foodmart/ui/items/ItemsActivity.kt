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

package com.raywenderlich.android.foodmart.ui.items

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.raywenderlich.android.foodmart.R
import com.raywenderlich.android.foodmart.model.Food
import com.raywenderlich.android.foodmart.model.events.CartEvent
import com.raywenderlich.android.foodmart.ui.Injection
import com.raywenderlich.android.foodmart.ui.cart.CartActivity
import com.raywenderlich.android.foodmart.ui.categories.CategoriesActivity
import kotlinx.android.synthetic.main.activity_items.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class ItemsActivity : AppCompatActivity(), ItemsContract.View, ItemsAdapter.ItemsAdapterListener {

  override lateinit var presenter: ItemsContract.Presenter
  private val adapter = ItemsAdapter(mutableListOf(), this)

  private var itemCount: TextView? = null
  private var itemCountCircle: FrameLayout? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_items)

    presenter = Injection.provideItemsPresenter(this)

    setupRecyclerView()
    setupCartIcon()
  }

  private fun setupCartIcon() {
    // Add an OnGlobalLayoutListener to allow creating cart icon correctly in lifecycle
    itemsRootView.viewTreeObserver.addOnGlobalLayoutListener {
      itemCount = findViewById(R.id.itemCount)
      itemCountCircle = findViewById(R.id.itemCountCircle)
      updateCartIcon()
    }
  }

  private fun setupRecyclerView() {
    itemsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    itemsRecyclerView.adapter = adapter
  }

  override fun onStart() {
    super.onStart()
    EventBus.getDefault().register(this)
  }

  override fun onStop() {
    super.onStop()
    EventBus.getDefault().unregister(this)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    super.onCreateOptionsMenu(menu)
    menuInflater.inflate(R.menu.activity_items, menu)
    val item = menu.findItem(R.id.cart_menu_item)
    item.actionView.setOnClickListener { menu.performIdentifierAction(item.itemId, 0) }
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.cart_menu_item -> startActivity(CartActivity.newIntent(this))
      R.id.add_all_menu_item -> presenter.addAllToCart()
      R.id.remove_all_menu_item -> presenter.clearCart()
      R.id.categories_menu_item -> showCategories()
    }
    return super.onOptionsItemSelected(item)
  }

  private fun showCategories() {
    startActivity(CategoriesActivity.newIntent(this))
  }

  private fun updateCartIcon() {
    val cartSize = presenter.cartSize()
    itemCount?.text = "$cartSize"
    itemCountCircle?.visibility = if (cartSize > 0) View.VISIBLE else View.INVISIBLE
  }

  override fun onResume() {
    super.onResume()
    presenter.start()
  }

  override fun showItems(items: List<Food>) {
    adapter.updateItems(items)
  }

  override fun removeItem(item: Food) {
    presenter.removeItem(item)
  }

  override fun addItem(item: Food) {
    presenter.addItem(item)
  }

  @Suppress("UNUSED_PARAMETER")
  @Subscribe(threadMode = ThreadMode.MAIN)
  fun onCartEvent(event: CartEvent) {
    updateCartIcon()
    adapter.notifyDataSetChanged()
  }
}


