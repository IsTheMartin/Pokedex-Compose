package me.ismartin.pokedexcompose.business.models

import androidx.compose.ui.graphics.Color
import me.ismartin.pokedexcompose.ui.theme.PokemonColor
import me.ismartin.pokedexcompose.ui.theme.Purple80

data class PokedexPokemon(
    val id: String,
    val name: String,
    val sprites: Sprites = Sprites(),
    val types: List<Pair<String, Color>>,
    val color: Color,
)

data class Sprites(
    val frontDefault: String = "",
    val backDefault: String? = null,
    val dreamWorldFrontDefault: String? = null,
    val homeFrontDefault: String? = null,
    val officialArtWorkFrontDefault: String? = null,
)

enum class PokemonTypeColor(val colorName: String, val color: Color) {
    NORMAL("normal", PokemonColor.Type.Normal),
    FIGHTING("fighting", PokemonColor.Type.Fighting),
    FLYING("flying", PokemonColor.Type.Flying),
    POISON("poison", PokemonColor.Type.Poison),
    GROUND("ground", PokemonColor.Type.Ground),
    ROCK("rock", PokemonColor.Type.Rock),
    BUG("bug", PokemonColor.Type.Bug),
    GHOST("ghost", PokemonColor.Type.Ghost),
    STEEL("steel", PokemonColor.Type.Steel),
    FIRE("fire", PokemonColor.Type.Fire),
    WATER("water", PokemonColor.Type.Water),
    GRASS("grass", PokemonColor.Type.Grass),
    ELECTRIC("electric", PokemonColor.Type.Electric),
    PSYCHIC("psychic", PokemonColor.Type.Psychic),
    ICE("ice", PokemonColor.Type.Ice),
    DRAGON("dragon", PokemonColor.Type.Dragon),
    DARK("dark", PokemonColor.Type.Dark),
    FAIRY("fairy", PokemonColor.Type.Fairy),
    UNKNOWN("unknown", PokemonColor.Type.Unknown),
    SHADOW("shadow", PokemonColor.Type.Shadow);

    companion object {
        fun getColor(type: String): Color {
            return values().find { it.colorName == type }?.color ?: Purple80
        }
    }
}

enum class PokemonBackgroundColor(val colorName: String, val color: Color) {
    RED("red", PokemonColor.Background.Red),
    BLUE("blue", PokemonColor.Background.Blue),
    YELLOW("yellow", PokemonColor.Background.Yellow),
    GREEN("green", PokemonColor.Background.Green),
    BLACK("black", PokemonColor.Background.Black),
    BROWN("brown", PokemonColor.Background.Brown),
    PURPLE("purple", PokemonColor.Background.Purple),
    GRAY("gray", PokemonColor.Background.Gray),
    WHITE("white", PokemonColor.Background.White),
    PINK("pink", PokemonColor.Background.Pink);

    companion object {
        fun getColor(color: String): Color {
            return PokemonBackgroundColor.values().find { it.colorName == color }?.color ?: Purple80
        }
    }
}
