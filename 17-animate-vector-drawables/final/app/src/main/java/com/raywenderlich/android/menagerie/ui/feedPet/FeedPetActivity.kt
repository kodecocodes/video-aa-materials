package com.raywenderlich.android.menagerie.ui.feedPet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setupUi()
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

    // TODO physics animation
  }
}