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

package com.raywenderlich.android.foodmart.ui.cart

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.raywenderlich.android.foodmart.R
import com.raywenderlich.android.foodmart.app.inflate
import com.raywenderlich.android.foodmart.model.Food
import kotlinx.android.synthetic.main.list_item_cart.view.*


class CartAdapter(private val items: MutableList<Food>, private val listener: CartAdapterListener) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
      ViewHolder(parent.inflate(R.layout.list_item_cart))

  override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) =
    holder.bind(items[position])

  override fun getItemCount() = items.size

  fun updateItems(items: List<Food>, notify: Boolean) {
    this.items.clear()
    this.items.addAll(items)
    if (notify) notifyDataSetChanged()
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var item: Food

    fun bind(item: Food) {
      this.item = item
      val context = itemView.context
      itemView.foodImage.setImageResource(context.resources.getIdentifier(item.thumbnail, null, context.packageName))
      itemView.foodName.text = item.name
      itemView.deleteButton.setOnClickListener {
        if (item.isInCart) {
          listener.removeItem(item)
        }
      }
    }
  }

  interface CartAdapterListener {
    fun removeItem(item: Food)
  }
}