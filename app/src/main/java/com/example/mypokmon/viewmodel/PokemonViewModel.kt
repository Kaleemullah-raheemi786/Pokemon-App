package com.example.mypokmon.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mypokmon.networking.ApiConfig
import com.example.mypokmon.Pokemon
import com.example.mypokmon.PokemonModel
import retrofit2.Call
import retrofit2.Response

class PokemonViewModel : ViewModel() {
    private val _showPokemon = MutableLiveData<List<Pokemon>>()
    val showPokemon : LiveData<List<Pokemon>> = _showPokemon

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getListPokemon (limit: Int, offset: Int){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListPokemon(limit, offset)
        client.enqueue(object: retrofit2.Callback<PokemonModel> {
            override fun onResponse(
                call: Call<PokemonModel>,
                response: Response<PokemonModel>
            ){
                _isLoading.value = false
                Log.d("tes", response.toString())
                if (response.isSuccessful){
                    _showPokemon.value = response.body()?.results
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PokemonModel>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object{
        private const val TAG = "PokemonViewModel"
    }
}
