package com.example.mypokmon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokmon.Pokemon
import com.example.mypokmon.databinding.ItemLayoutBinding

import com.squareup.picasso.Picasso

class ListPokemonAdapter : RecyclerView.Adapter<ListPokemonAdapter.ListViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback
    private val listPokemon = ArrayList<Pokemon>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(poke: List<Pokemon>){
        listPokemon.clear()
        listPokemon.addAll(poke)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(var binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pokes: Pokemon){
            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(pokes) }
            binding.apply{
          Picasso.get().load(pokes.getImageUrl()).into(image)
                name.text = pokes.name
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding =
            ItemLayoutBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    interface OnItemClickCallback {
        fun onItemClicked(pokes: Pokemon)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listPokemon[position])
    }

    override fun getItemCount(): Int = listPokemon.size

}