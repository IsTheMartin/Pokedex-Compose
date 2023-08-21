package me.ismartin.pokedexcompose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.ismartin.pokedexcompose.data.remote.PokeApiService
import me.ismartin.pokedexcompose.data.remote.RemoteRepository
import me.ismartin.pokedexcompose.data.remote.RemoteRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val IS_DEBUG = true // todo: check BuildConfig
    private const val TIMEOUT = 20L

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (IS_DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(PokeApiService.BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun providePokeApiService(retrofit: Retrofit) = retrofit.create(PokeApiService::class.java)

    @Provides
    @Singleton
    fun provideRemoteRepository(pokeApiService: PokeApiService): RemoteRepository =
        RemoteRepositoryImpl(pokeApiService)
}
