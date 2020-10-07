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

package com.raywenderlich.android.foodmart.ui.categories

import android.support.v4.view.ViewPager
import android.view.View


class DepthPageTransformer : ViewPager.PageTransformer {

  override fun transformPage(view: View, position: Float) {
    val pageWidth = view.width

    when {
      position < -1 -> // [-Infinity,-1)
        // This page is way off-screen to the left.
        view.alpha = 0f
      position <= 0 -> { // [-1,0]
        // Fade the page out.
        view.alpha = 1f + position

        // Counteract the default slide transition
        view.translationX = pageWidth * -position

        // Scale the page down (between MIN_SCALE and 1)
        val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position))
        view.scaleX = scaleFactor
        view.scaleY = scaleFactor
      }
      position <= 1 -> { // (0, 1]
        // Use the default slide transition when moving to the right page
        view.alpha = 1f
        view.translationX = 0f
        view.scaleX = 1f
        view.scaleY = 1f
      }
      else -> // (1,+Infinity]
        // This page is way off-screen to the right.
        view.alpha = 0f
    }
  }

  companion object {
    private val MIN_SCALE = 0.75f
  }
}