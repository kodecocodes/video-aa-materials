package com.raywenderlich.android.menagerie.ui.login.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.core.view.doOnLayout
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
    super(context, attributeSet, defStyleAttr) {
  }

  @RequiresApi(Build.VERSION_CODES.N)
  fun transformToProgress() {

  }

  inline fun onClick(crossinline onClick: () -> Unit) {
    binding.actionButton.setOnClickListener { onClick() }
  }
}