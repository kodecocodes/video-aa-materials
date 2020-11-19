package com.raywenderlich.android.menagerie.ui.main.adapter

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
    onItemClick: (Pet) -> Unit,
    onPetSleepClick: (Pet) -> Unit
  ) {
    binding.petAvatar.load(data.image)
    binding.petName.text = data.name
    binding.petSleep.setImageResource(if (data.isSleeping) R.drawable.ic_bedtime_24 else R.drawable.ic_sunny_24)

    // todo transition
    binding.petSleep.setOnClickListener { onPetSleepClick(data) }
    binding.root.setOnClickListener { onItemClick(data) }
  }
}