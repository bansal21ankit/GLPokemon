package com.example.glpokemon.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glpokemon.network.PokemonDetails
import com.example.glpokemon.network.PokemonRepository
import kotlinx.coroutines.launch
import kotlin.random.Random

class PokemonViewModel : ViewModel() {
    var mPokemonDetails = MutableLiveData<PokemonDetails>()
    var mFetching = MutableLiveData<Boolean>()
    private var mPokemonCount: Int? = null

    fun fetchRandomPokemon() {
        viewModelScope.launch {
            mFetching.postValue(true)

            if (mPokemonCount == null) mPokemonCount = PokemonRepository.getPokemonCount()
            val randomId = randomize(mPokemonCount ?: 1)
            val response = PokemonRepository.getPokemonDetails(randomId)

            mFetching.postValue(false)
            if (response.isSuccessful) mPokemonDetails.postValue(response.body())
            else fetchRandomPokemon()
        }
    }

    private fun randomize(limit: Int) = Random.nextInt(1, limit + 1)
}

fun String.capitalizeFirst(): String {
    return when (length) {
        0 -> this
        1 -> this.uppercase()
        else -> this[0].uppercaseChar() + substring(1)
    }
}