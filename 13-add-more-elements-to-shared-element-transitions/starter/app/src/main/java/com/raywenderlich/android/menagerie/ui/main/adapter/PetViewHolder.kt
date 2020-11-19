package com.raywenderlich.android.menagerie.ui.main.adapter

import android.view.View
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.raywenderlich.android.menagerie.R
import com.raywenderlich.android.menagerie.data.model.Pet
import com.raywenderlich.android.menagerie.databinding.ItemPetBinding

class PetViewHolder(
  private val binding: ItemPetBinding
) : RecyclerView.ViewHolder(binding.root) {

  fun bindData(
    data: Pet,
    onItemClick: (Pet, Array<Pair<View, String>>) -> Unit,
    onPetSleepClick: (Pet) -> Unit
  ) {
    binding.petAvatar.load(data.image)
    binding.petName.text = data.name
    binding.petSleep.setImageResource(if (data.isSleeping) R.drawable.ic_bedtime_24 else R.drawable.ic_sunny_24)

    binding.petSleep.setOnClickListener { onPetSleepClick(data) }
    binding.root.setOnClickListener {
      val petAvatar: Pair<View, String> =
        Pair.create(binding.petAvatar, itemView.resources.getString(R.string.transitionPetAvatar))

      onItemClick(data, arrayOf(petAvatar))
    }
  }
}