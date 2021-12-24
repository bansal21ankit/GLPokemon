package com.example.glpokemon.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 1,
        @Query("offset") offset: Int = 0
    ): PokemonListResponse

    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(
        @Path("id") id: Int = 1
    ): Response<PokemonDetails>
}