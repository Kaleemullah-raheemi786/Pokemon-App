package com.example.mypokmon.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mypokmon.Pokemon
import com.example.mypokmon.R
import com.example.mypokmon.adapter.ListPokemonAdapter
import com.example.mypokmon.databinding.FragmentPokemonListBinding
import com.example.mypokmon.viewmodel.PokemonViewModel

class PokemonListFragment : Fragment(){
    private val adapter = ListPokemonAdapter()
    lateinit var binding: FragmentPokemonListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPokemonListBinding.inflate(layoutInflater, container, false)

        val pokeminListViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[PokemonViewModel::class.java]
        pokeminListViewModel.getListPokemon(100, 0)
        binding.recycleView.adapter = adapter

        val layoutManager = GridLayoutManager(context, 3)
        binding.recycleView.layoutManager = layoutManager

        pokeminListViewModel.isLoading.observe(viewLifecycleOwner){showLoading(it)}
        pokeminListViewModel.showPokemon.observe(viewLifecycleOwner) { results -> setPokemon(results) }

        return binding.root
    }

    private fun setPokemon(results: List<Pokemon>) {
        adapter.setData(results)
        adapter.setOnItemClickCallback(object : ListPokemonAdapter.OnItemClickCallback {
            override fun onItemClicked(pokes: Pokemon) {
                findNavController().navigate(
                    R.id.action_pokemonListFragment_to_pokemonDetailListFragment,
                    Bundle().apply {
                        putString(PokemonDetailListFragment.NAME, pokes.name)
                        putString(PokemonDetailListFragment.IMAGE, pokes.getImageUrl())
                        putString(PokemonDetailListFragment.ID, pokes.getId())
                    })
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

