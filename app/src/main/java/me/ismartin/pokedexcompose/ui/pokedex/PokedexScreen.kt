package me.ismartin.pokedexcompose.ui.pokedex

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import kotlinx.coroutines.flow.flowOf
import me.ismartin.pokedexcompose.R
import me.ismartin.pokedexcompose.business.models.PokedexPokemon
import me.ismartin.pokedexcompose.business.models.Sprites
import me.ismartin.pokedexcompose.ui.theme.PokemonColor

@Composable
fun PokedexScreen(
    pokemonList: LazyPagingItems<PokedexPokemon>
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                columns = GridCells.Fixed(2),
            ) {
                items(pokemonList) {
                    if (it != null) {
                        PokemonItem(pokemon = it)
                    }
                }
            }
            LoadingIndicator(
                modifier = Modifier
                    .fillMaxWidth(),
                isLoading = pokemonList.loadState.append == LoadState.Loading
            )
        }
    }
}

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier, isLoading: Boolean) {
    if (isLoading) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun PokemonItem(pokemon: PokedexPokemon) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(7.dp))
            .background(pokemon.color)
    ) {
        Box {
            Column(
                modifier = Modifier
                    .padding(horizontal = 6.dp, vertical = 16.dp)
                    .zIndex(2f)
            ) {
                Text(
                    modifier = Modifier.widthIn(max = 130.dp),
                    text = pokemon.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(18f, TextUnitType.Sp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                PokemonTypeItem(typeName = pokemon.types)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(modifier = Modifier.align(Alignment.BottomEnd)) {
                    Text(
                        modifier = Modifier.align(Alignment.End),
                        text = pokemon.id,
                        fontWeight = FontWeight.Bold,
                        fontSize = TextUnit(22f, TextUnitType.Sp),
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    AsyncImage(
                        modifier = Modifier
                            .height(100.dp)
                            .width(100.dp),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(pokemon.sprites.dreamWorldFrontDefault ?: pokemon.sprites.frontDefault)
                            .decoderFactory(SvgDecoder.Factory())
                            .build(),
                        contentDescription = pokemon.name,
                        contentScale = ContentScale.Fit,
                        placeholder = painterResource(id = R.drawable.pikachu_placeholder)
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonTypeItem(typeName: List<Pair<String, Color>>) {
    Box(
        modifier = Modifier
            .height(60.dp)
            .widthIn(min = 100.dp)
    ) {
        LazyColumn {
            items(typeName) {
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(shape = RoundedCornerShape(5.dp))
                        .background(it.second)
                        /*.shadow(
                            elevation = 1.dp,
                            spotColor = Color.Black,
                            shape = RoundedCornerShape(5.dp)
                        )*/
                ) {
                    Text(
                        text = it.first,
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 2.dp)
                            .align(Alignment.Center),
                        color = Color.White,
                        fontSize = TextUnit(10f, TextUnitType.Sp)
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PokedexPreview() {
    val pokemonsList = listOf(
        PokedexPokemon(
            id = "025",
            name = "Charmander",
            sprites = Sprites(
                frontDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png",
                backDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/3.png",
                dreamWorldFrontDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/3.svg"
            ),
            types = listOf(Pair("Electric", PokemonColor.Type.Electric)),
            color = Color.White
        ),
        PokedexPokemon(
            id = "740",
            name = "Very long text for a pokemon",
            sprites = Sprites(
                frontDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/704.png",
                backDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/704.png",
                dreamWorldFrontDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/704.svg"
            ),
            types = listOf(
                Pair("Fighting", PokemonColor.Type.Grass),
                Pair("Ice", PokemonColor.Type.Fire)
            ),
            color = PokemonColor.Background.Red
        )
    )
    PokedexScreen(pokemonList = flowOf(PagingData.from(pokemonsList)).collectAsLazyPagingItems())
}

fun <T : Any> LazyGridScope.items(
    items: LazyPagingItems<T>,
    key: ((item: T) -> Any)? = null,
    itemContent: @Composable LazyGridItemScope.(item: T?) -> Unit
) {
    items(
        count = items.itemCount,
        key = if (key == null) null else { index ->
            val item = items.peek(index)
            if (item == null) {
                PagingPlaceholderKey(index)
            } else {
                key(item)
            }
        }
    ) { index ->
        itemContent(items[index])
    }
}

@SuppressLint("BanParcelableUsage")
private data class PagingPlaceholderKey(private val index: Int) : Parcelable {
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(index)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @Suppress("unused")
        @JvmField
        val CREATOR: Parcelable.Creator<PagingPlaceholderKey> =
            object : Parcelable.Creator<PagingPlaceholderKey> {
                override fun createFromParcel(parcel: Parcel) =
                    PagingPlaceholderKey(parcel.readInt())

                override fun newArray(size: Int) = arrayOfNulls<PagingPlaceholderKey?>(size)
            }
    }
}
