package com.raywenderlich.android.menagerie.ui.login.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.core.view.doOnLayout
import androidx.core.view.updateLayoutParams
import com.raywenderlich.android.menagerie.databinding.ViewProgressButtonBinding

class ProgressButton : FrameLayout {

  val binding by lazy {
    ViewProgressButtonBinding.inflate(
      LayoutInflater.from(context)
    )
  }

  private var initialButtonWidth = 0
  private var targetButtonWidth = 0

  init {
    addView(binding.root)
    binding.actionButton.doOnLayout {
      initialButtonWidth = it.measuredWidth
      targetButtonWidth = it.measuredHeight
    }
  }

  constructor(context: Context) :
    super(context)

  constructor(context: Context, attributeSet: AttributeSet) :
    super(context, attributeSet)

  constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) :
    super(context, attributeSet, defStyleAttr)

  @RequiresApi(Build.VERSION_CODES.N)
  fun transformToProgress() {
    val drawable = binding.actionButton.background as? GradientDrawable
    val targetWidth = targetButtonWidth
    val targetCornerRadius = targetButtonWidth

    val cornerAnimator =
      ValueAnimator.ofFloat(drawable?.cornerRadius ?: 0f, targetCornerRadius.toFloat())
    val widthAnimator = ValueAnimator.ofInt(binding.actionButton.measuredWidth, targetWidth)
    val progressBarAlphaAnimator = ObjectAnimator.ofFloat(binding.progressBar, "alpha", 0f, 1f)

    cornerAnimator.duration = 1000
    widthAnimator.duration = 1000
    progressBarAlphaAnimator.duration = 1000

    cornerAnimator.addUpdateListener {
      drawable?.cornerRadius = it.animatedValue as Float

      binding.actionButton.background = drawable
    }

    widthAnimator.addUpdateListener {
      binding.actionButton.updateLayoutParams {
        this.width = it.animatedValue as Int
      }

      binding.actionButton.textSize = it.animatedFraction * binding.actionButton.textSize
    }

    binding.progressBar.alpha = 0f
    binding.progressBar.visibility = View.VISIBLE

    widthAnimator.start()
    cornerAnimator.start()
    progressBarAlphaAnimator.start()
  }

  inline fun onClick(crossinline onClick: () -> Unit) {
    binding.actionButton.setOnClickListener { onClick() }
  }
}