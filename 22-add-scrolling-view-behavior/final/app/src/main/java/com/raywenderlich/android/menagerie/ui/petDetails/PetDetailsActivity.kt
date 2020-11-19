package com.raywenderlich.android.menagerie.ui.petDetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import com.raywenderlich.android.menagerie.R
import com.raywenderlich.android.menagerie.data.model.Pet
import com.raywenderlich.android.menagerie.databinding.ActivityPetDetailsBinding
import com.raywenderlich.android.menagerie.ui.feedPet.FeedPetActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class PetDetailsActivity : AppCompatActivity(), PetDetailsView {

  companion object {
    private const val KEY_PET = "pet"

    fun getIntent(
      context: Context,
      pet: Pet
    ) = Intent(context, PetDetailsActivity::class.java).apply {
      putExtra(KEY_PET, pet)
    }
  }

  private val viewModel by viewModels<PetDetailsViewModel>()

  private val binding by lazy { ActivityPetDetailsBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.setView(this)
    setContentView(binding.root)
    viewModel.setView(this)
    setupUi()
  }

  private fun setupUi() {
    val data = intent.getSerializableExtra(KEY_PET) as? Pet ?: return
    viewModel.setPet(data)

    viewModel.petData.observe(this, {
      val pet = it ?: return@observe

      binding.collapsingToolbar.setCollapsedTitleTextColor(getColor(R.color.white))
      binding.collapsingToolbar.setExpandedTitleColor(getColor(R.color.white))

      binding.petDetailsTitle.title = getString(R.string.petDetailsTitle, pet.name)
      binding.petImage.setImageResource(pet.image)
      binding.petName.text = pet.name
      binding.petDescription.text = pet.description

      binding.petSleep.setImageResource(if (pet.isSleeping) R.drawable.ic_bedtime_24 else R.drawable.ic_sunny_24)
      binding.petSleep.setOnClickListener { viewModel.onPetSleepTap(pet) }

      binding.feedPetButton.setOnClickListener {
        viewModel.onPetFeedTap(pet)
      }
    })

    binding.appBarLayout.addOnOffsetChangedListener(
      AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
        val scrollRange = appBarLayout.totalScrollRange
        val percentScrolled = abs(verticalOffset / scrollRange.toFloat())

        binding.petSleepToolbar.alpha = percentScrolled
        binding.petSleep.alpha = 1 - percentScrolled
      }
    )
  }

  override fun onBackPressed() {
    supportFinishAfterTransition()
  }

  override fun showFeedingSleepingPetMessage() {
    Toast.makeText(this, getString(R.string.cannotFeedSleepingPetMessage), Toast.LENGTH_SHORT)
      .show()
  }

  override fun feedPet(pet: Pet) {
    startActivity(FeedPetActivity.getIntent(this, pet))
  }
}