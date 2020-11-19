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
import androidx.core.animation.doOnEnd
import androidx.core.view.doOnLayout
import androidx.core.view.updateLayoutParams
import com.raywenderlich.android.menagerie.databinding.ViewProgressButtonBinding

class ProgressButton : FrameLayout {

  val binding by lazy {
    ViewProgressButtonBinding.inflate(
      LayoutInflater.from(context)
    )
  }

  private val animations = mutableListOf<ValueAnimator>()

  private var initialButtonWidth = 0
  private var targetButtonWidth = 0
  private var initialTextSize = 0f

  init {
    addView(binding.root)
    binding.actionButton.doOnLayout {
      initialButtonWidth = it.measuredWidth
      targetButtonWidth = it.measuredHeight
      initialTextSize = binding.actionButton.textSize
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
    val textSizeAnimator = ValueAnimator.ofFloat(initialTextSize, 0f)

    cornerAnimator.duration = 1000
    widthAnimator.duration = 1000
    progressBarAlphaAnimator.duration = 1000
    textSizeAnimator.duration = 1000

    cornerAnimator.addUpdateListener {
      drawable?.cornerRadius = it.animatedValue as Float

      binding.actionButton.background = drawable
    }

    widthAnimator.addUpdateListener {
      binding.actionButton.updateLayoutParams {
        this.width = it.animatedValue as Int
      }
    }

    textSizeAnimator.addUpdateListener {
      val textSizeSp = (it.animatedValue as Float) / resources.displayMetrics.density

      binding.actionButton.textSize = textSizeSp
    }

    binding.progressBar.alpha = 0f
    binding.progressBar.visibility = View.VISIBLE

    animations.addAll(
      listOf(
        widthAnimator,
        cornerAnimator,
        progressBarAlphaAnimator,
        textSizeAnimator
      )
    )

    widthAnimator.start()
    cornerAnimator.start()
    textSizeAnimator.start()
    progressBarAlphaAnimator.start()
  }

  fun reverseAnimation(onReverseAnimationEnd: () -> Unit) {
    animations.forEach { animator ->
      animator.reverse()

      if (animations.indexOf(animator) == animations.lastIndex) {
        animator.doOnEnd {
          onReverseAnimationEnd()
          animations.clear()
        }
      }
    }
  }

  inline fun onClick(crossinline onClick: () -> Unit) {
    binding.actionButton.setOnClickListener { onClick() }
  }
}