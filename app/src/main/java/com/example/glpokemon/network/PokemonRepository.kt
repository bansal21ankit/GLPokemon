package com.example.glpokemon.network

import com.example.glpokemon.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PokemonRepository {
    private val mApiService by lazy {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(PokemonApiService::class.java)
    }

    suspend fun getPokemonCount() = mApiService.getPokemonList().count
    suspend fun getPokemonDetails(id: Int) = mApiService.getPokemonDetails(id)
}