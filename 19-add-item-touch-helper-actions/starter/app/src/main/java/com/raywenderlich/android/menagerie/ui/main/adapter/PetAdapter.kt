package com.raywenderlich.android.menagerie.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.android.menagerie.data.model.Pet
import com.raywenderlich.android.menagerie.databinding.ItemPetBinding

class PetAdapter(
  private val onItemClick: (Pet, Array<Pair<View, String>>) -> Unit,
  private val onPetSleepClick: (Pet) -> Unit
) : RecyclerView.Adapter<PetViewHolder>() {

  private val items = mutableListOf<Pet>()

  override fun getItemCount(): Int = items.size

  fun setData(data: List<Pet>) { // todo item add/remove anim
    this.items.clear()
    this.items.addAll(data)
    notifyDataSetChanged()
  }

  fun onItemMoved(oldPosition: Int, newPosition: Int) {

  }

  fun onItemSwiped(position: Int) {

  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
    val binding = ItemPetBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    return PetViewHolder(binding)
  }

  override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
    holder.bindData(items[position], onItemClick, onPetSleepClick)
  }
}