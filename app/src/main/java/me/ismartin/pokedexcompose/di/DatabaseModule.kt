package me.ismartin.pokedexcompose.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.ismartin.pokedexcompose.business.PokedexMediator
import me.ismartin.pokedexcompose.data.local.LocalRepository
import me.ismartin.pokedexcompose.data.local.LocalRepositoryImpl
import me.ismartin.pokedexcompose.data.local.PokedexDatabase
import me.ismartin.pokedexcompose.data.local.daos.PokemonDao
import me.ismartin.pokedexcompose.data.local.daos.RemoteKeyDao
import me.ismartin.pokedexcompose.data.local.daos.SimplePokemonDao
import me.ismartin.pokedexcompose.data.local.daos.TypeDao
import me.ismartin.pokedexcompose.data.local.entities.PokemonEntity
import me.ismartin.pokedexcompose.data.local.repositories.SimplePokemonRepository
import me.ismartin.pokedexcompose.data.local.repositories.SimplePokemonRepositoryImpl
import me.ismartin.pokedexcompose.data.remote.RemoteRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "pokedex"

    @Provides
    @Singleton
    fun providePokedexDatabase(@ApplicationContext context: Context) = Room
        .databaseBuilder(
            context = context,
            klass = PokedexDatabase::class.java,
            name = DATABASE_NAME
        )
        .build()

    @Provides
    fun providePokemonDao(pokedexDatabase: PokedexDatabase) = pokedexDatabase.pokemonDao()

    @Provides
    fun provideTypeDao(pokedexDatabase: PokedexDatabase) = pokedexDatabase.typeDao()

    @Provides
    fun provideRemoteKeyDao(pokedexDatabase: PokedexDatabase) = pokedexDatabase.remoteKeyDao()

    @Provides
    fun provideSimplePokemonDao(pokedexDatabase: PokedexDatabase) = pokedexDatabase.simplePokemonDao()

    @Provides
    @Singleton
    fun provideLocalRepository(
        pokemonDao: PokemonDao,
        typeDao: TypeDao,
        remoteKeyDao: RemoteKeyDao,
        simplePokemonDao: SimplePokemonDao,
    ): LocalRepository = LocalRepositoryImpl(
        pokemonDao = pokemonDao,
        typeDao = typeDao,
        remoteKeyDao = remoteKeyDao,
        simplePokemonDao = simplePokemonDao
    )

    @Provides
    @Singleton
    fun provideSimplePokemonRepository(
        simplePokemonDao: SimplePokemonDao
    ): SimplePokemonRepository = SimplePokemonRepositoryImpl(
        simplePokemonDao = simplePokemonDao
    )

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun providePokedexPager(
        remoteRepository: RemoteRepository,
        localRepository: LocalRepository,
        database: PokedexDatabase
    ): Pager<Int, PokemonEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            remoteMediator = PokedexMediator(
                remoteRepository = remoteRepository,
                localRepository = localRepository,
                database = database
            ),
            pagingSourceFactory = {
                localRepository.getPagedPokedex()
            }
        )
    }
}
