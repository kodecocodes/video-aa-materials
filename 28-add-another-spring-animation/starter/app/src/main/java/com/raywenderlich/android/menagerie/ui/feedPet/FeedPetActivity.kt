package com.raywenderlich.android.menagerie.ui.feedPet

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import coil.load
import com.raywenderlich.android.menagerie.R
import com.raywenderlich.android.menagerie.data.model.Pet
import com.raywenderlich.android.menagerie.databinding.ActivityFeedPetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedPetActivity : AppCompatActivity() {

  companion object {
    private const val KEY_PET = "pet"

    fun getIntent(context: Context, pet: Pet) = Intent(context, FeedPetActivity::class.java).apply {
      putExtra(KEY_PET, pet)
    }
  }

  private val binding by lazy { ActivityFeedPetBinding.inflate(layoutInflater) }
  private val feedPetViewModel by viewModels<FeedPetViewModel>()

  // animation
  private var horizontalPositionDifference = 0f
  private val springForce by lazy {
    SpringForce(0f).apply {
      stiffness = SpringForce.STIFFNESS_MEDIUM
      dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setupUi()
  }

  @SuppressLint("ClickableViewAccessibility")
  private fun setupUi() {
    val data = intent.getSerializableExtra(KEY_PET) as? Pet ?: return

    feedPetViewModel.setPet(data)
    feedPetViewModel.petData.observe(this, { petData ->

      if (petData != null) {
        binding.feedPetToolbar.title = getString(R.string.feedPetTitle, petData.name)
        binding.petImage.load(petData.image)
      }
    })

    val springChocolateCookie = SpringAnimation(binding.chocolateCookie, DynamicAnimation.TRANSLATION_X)
      .setSpring(springForce)

    val springCookie = SpringAnimation(binding.cookie, DynamicAnimation.TRANSLATION_X)
      .setSpring(springForce)

    binding.chocolateCookie.setOnTouchListener(buildTouchListener(springChocolateCookie))
    binding.cookie.setOnTouchListener(buildTouchListener(springCookie))
  }

  @SuppressLint("ClickableViewAccessibility")
  private fun buildTouchListener(springAnimation: SpringAnimation) = View.OnTouchListener { view, event ->
    when (event?.action) {
      MotionEvent.ACTION_DOWN -> {
        horizontalPositionDifference = event.rawX - (view?.x ?: 0f)

        springAnimation.cancel()
      }

      MotionEvent.ACTION_MOVE -> {
        view?.x = event.rawX - horizontalPositionDifference
      }

      MotionEvent.ACTION_UP -> springAnimation.start()
    }

    true
  }
}