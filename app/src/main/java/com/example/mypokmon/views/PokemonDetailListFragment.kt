package com.example.mypokmon.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mypokmon.databinding.FragmentPokemonDetailListBinding
import com.example.mypokmon.model.AboutModel
import com.example.mypokmon.viewmodel.DetailModel
import com.example.mypokmon.viewmodel.DetailPokemonListViewModel
import com.squareup.picasso.Picasso

class PokemonDetailListFragment : Fragment() {
    private lateinit var binding: FragmentPokemonDetailListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  =  FragmentPokemonDetailListBinding.inflate(layoutInflater, container, false)

        val detailPokemonViewModel = ViewModelProvider(this)[DetailPokemonListViewModel::class.java]

        detailPokemonViewModel.detailPokemon.observe(viewLifecycleOwner){ detailPokemon ->
            setDetailPokemon(detailPokemon
            )
        }

        detailPokemonViewModel.aboutPokemon.observe(viewLifecycleOwner){ flavorPokemon ->
            setFlavor(flavorPokemon
            )
        }

        val name = requireArguments().getString(NAME)
        if (name != null) {
            detailPokemonViewModel.getDetailPokemon(name)
            detailPokemonViewModel.getDescriptionPokemon(name)
            Log.d("sss", name.toString())
        }

        return binding.root
    }

    private fun setFlavor(about: AboutModel?) {
        binding.apply {
            pbDetail.visibility = View.GONE
            constraintLayout.visibility = View.VISIBLE
            tvFlavorText.text = about?.flavor_text_entries!![0].flavor_text
        }
    }

    private fun setDetailPokemon(detailPokemon: DetailModel?) {
        binding.apply {
            pbDetail.visibility = View.GONE
            constraintLayout.visibility = View.VISIBLE
            tvID.text = detailPokemon?.getIdString()
            tvName.text = detailPokemon?.name
            tvWeight.text = detailPokemon?.getWeightString()
            tvHeight.text = detailPokemon?.getHeightString()
            tvBaseExperience.text = detailPokemon?.experience.toString()
            Picasso.get().load(detailPokemon?.getImageUrl()).into(imageView)
        }
    }

    companion object {
        const val NAME = "name"
        const val IMAGE = "image"
        const val ID = "id"
    }
}