package com.example.mypokmon.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mypokmon.model.AboutModel
import com.example.mypokmon.networking.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPokemonListViewModel(application: Application) : AndroidViewModel(application) {

    private val _detailPokemon = MutableLiveData<DetailModel>()
    val detailPokemon: LiveData<DetailModel> = _detailPokemon

    private val _aboutPokemon = MutableLiveData<AboutModel>()
    val aboutPokemon: LiveData<AboutModel> = _aboutPokemon

    fun getDetailPokemon(pokemon_id: String) {
        val client = ApiConfig.getApiService().getPokemon(pokemon_id)
        client.enqueue(object : Callback<DetailModel> {
            override fun onResponse(
                call: Call<DetailModel>,
                response: Response<DetailModel>
            ) {
                Log.d("tess", response.toString())
                if (response.isSuccessful) {
                    _detailPokemon.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailModel>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getDescriptionPokemon(pokemon_id: String) {
        val client = ApiConfig.getApiService().getDetailPokemon(pokemon_id)
        client.enqueue(object : Callback<AboutModel> {
            override fun onResponse(
                call: Call<AboutModel>,
                response: Response<AboutModel>
            ) {
                Log.d("tess", response.toString())
                if (response.isSuccessful) {
                    _aboutPokemon.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<AboutModel>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })

    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}