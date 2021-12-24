package com.example.glpokemon.network

import androidx.annotation.Keep

@Keep
data class PokemonListResponse(val count: Int?)

@Keep
data class PokemonDetails(
    val moves: List<Moves>?,
    val stats: List<Stats>?,
    val name: String?,
    val sprites: Sprites?
)

@Keep
data class Moves(val move: Move?)

@Keep
data class Move(val name: String?)

@Keep
data class Stats(val base_stat: Int?, val stat: Stat?)

@Keep
data class Stat(val name: String?)

@Keep
data class Sprites(val front_default: String?, val back_default: String?)