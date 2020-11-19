package com.raywenderlich.android.menagerie.ui.main.sleepingPets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.raywenderlich.android.menagerie.data.model.Pet
import com.raywenderlich.android.menagerie.databinding.FragmentSleepingPetsBinding
import com.raywenderlich.android.menagerie.ui.main.adapter.PetAdapter
import com.raywenderlich.android.menagerie.ui.petDetails.PetDetailsActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SleepingPetsFragment : Fragment(), SleepingPetsView {

  private var binding: FragmentSleepingPetsBinding? = null
  private val adapter by lazy { PetAdapter(::onSleepingPetClick, viewModel::onPetSleepClick) }
  private val viewModel by viewModels<SleepingPetsViewModel>()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentSleepingPetsBinding.inflate(inflater, container, false)

    return binding?.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel.setView(this)
    val petList = binding?.petList ?: return

    petList.layoutManager = LinearLayoutManager(requireActivity())
    petList.adapter = adapter

    viewModel.sleepingPets.observe(viewLifecycleOwner, { sleepingPets ->
      if (sleepingPets != null) {
        adapter.setData(sleepingPets)
      }
    })
  }

  private fun onSleepingPetClick(
    pet: Pet,
    transitionPairs: Array<Pair<View, String>>
  ) {
    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
      requireActivity(),
      *transitionPairs
    )

    startActivity(PetDetailsActivity.getIntent(requireActivity(), pet), options.toBundle())
  }
}