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

package com.raywenderlich.android.foodmart.ui.checkout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import com.raywenderlich.android.foodmart.R
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : AppCompatActivity() {

  private var xPositionDiff = 0f
  private var yPositionDiff = 0f

  private val springForce: SpringForce by lazy {
    SpringForce(0f).apply {
      stiffness = SpringForce.STIFFNESS_LOW
      dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
    }
  }

  private val springAnimationX: SpringAnimation by lazy {
    SpringAnimation(donut, DynamicAnimation.TRANSLATION_X).setSpring(springForce)
  }

  private val springAnimationY: SpringAnimation by lazy {
    SpringAnimation(donut, DynamicAnimation.TRANSLATION_Y).setSpring(springForce)
  }

  companion object {
    fun newIntent(context: Context) = Intent(context, CheckoutActivity::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_checkout)

    title = getString(R.string.checkout_title)

    setupTouchListener()
  }

  private fun setupTouchListener() {

    donut.setOnTouchListener { view, motionEvent ->

      when(motionEvent?.action) {

        MotionEvent.ACTION_DOWN -> {
          xPositionDiff = motionEvent.rawX - view.x
          yPositionDiff = motionEvent.rawY - view.y

          springAnimationX.cancel()
          springAnimationY.cancel()
        }

        MotionEvent.ACTION_MOVE -> {
          donut.x = motionEvent.rawX - xPositionDiff
          donut.y = motionEvent.rawY - yPositionDiff
        }

        MotionEvent.ACTION_UP -> {
          springAnimationX.start()
          springAnimationY.start()
        }
      }

      true
    }
  }
}
