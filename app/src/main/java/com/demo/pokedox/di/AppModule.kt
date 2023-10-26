package com.demo.pokedox.di

import com.demo.pokedox.data.remote.PokeApi
import com.demo.pokedox.data.repository.PokemonRepository
import com.demo.pokedox.data.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun providePokemonRepository(
        api : PokeApi
    ) = PokemonRepository(api = api)


    @Singleton
    @Provides
    fun providePokeApi(): PokeApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(PokeApi::class.java)
    }

}