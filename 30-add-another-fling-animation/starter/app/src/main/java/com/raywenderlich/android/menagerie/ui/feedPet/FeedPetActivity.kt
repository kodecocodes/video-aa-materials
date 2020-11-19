package com.raywenderlich.android.menagerie.ui.feedPet

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
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
  private val cookieFlingAnimationX by lazy {
    FlingAnimation(binding.cookie, DynamicAnimation.X).setFriction(1f)
  }

  private val cookieFlingAnimationY by lazy {
    FlingAnimation(binding.cookie, DynamicAnimation.Y).setFriction(1f)
  }

  private val cookieGestureListener = object : GestureDetector.SimpleOnGestureListener() {

    override fun onDown(e: MotionEvent?): Boolean = true

    override fun onFling(
      e1: MotionEvent?,
      e2: MotionEvent?,
      velocityX: Float,
      velocityY: Float
    ): Boolean {
      if (binding.cookie.visibility == View.VISIBLE) {
        cookieFlingAnimationX.setStartVelocity(velocityX)
        cookieFlingAnimationY.setStartVelocity(velocityY)

        cookieFlingAnimationX.start()
        cookieFlingAnimationY.start()
      }

      return true
    }
  }

  private val cookieGestureDetector by lazy {
    GestureDetector(this, cookieGestureListener)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setupUi()
    setupFlingBoxes()
    setupFlingEndListener()
  }

  private fun setupUi() {
    val data = intent.getSerializableExtra(KEY_PET) as? Pet ?: return

    feedPetViewModel.setPet(data)
    feedPetViewModel.petData.observe(this, { petData ->

      if (petData != null) {
        binding.feedPetToolbar.title = getString(R.string.feedPetTitle, petData.name)
        binding.petImage.load(petData.image)
      }
    })

   binding.cookie.setOnTouchListener { _, event ->
     cookieGestureDetector.onTouchEvent(event)

     true
   }
  }

  private fun setupFlingBoxes() {
    binding.cookie.doOnLayout {
      val displayMetrics = DisplayMetrics()
      display?.getRealMetrics(displayMetrics)

      val width = displayMetrics.widthPixels
      val height = displayMetrics.heightPixels

      cookieFlingAnimationX.setMinValue(0f).setMaxValue((width - it.width).toFloat())
      cookieFlingAnimationY.setMinValue(0f).setMaxValue(height - it.height * 2f)
    }
  }

  private fun setupFlingEndListener() {
    cookieFlingAnimationX.addEndListener { _, _, _, _ ->
      if (isPetTouchingCookie(binding.cookie, binding.petImage)) {
        binding.cookie.visibility = View.GONE
        Toast.makeText(this, "Omnomnonmonmnonm", Toast.LENGTH_SHORT).show()
      }
    }

    cookieFlingAnimationY.addEndListener { _, _, _, _ ->
      if (isPetTouchingCookie(binding.cookie, binding.petImage)) {
        binding.cookie.visibility = View.GONE
        Toast.makeText(this, "Omnomnonmonmnonm", Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun isPetTouchingCookie(cookie: View, pet: View): Boolean {
    val cookieRect = Rect()
    cookie.getHitRect(cookieRect)

    val petRect = Rect()
    pet.getHitRect(petRect)

    return Rect.intersects(cookieRect, petRect)
  }
}