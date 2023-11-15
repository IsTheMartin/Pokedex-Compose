package me.ismartin.pokedexcompose.data.remote.models.specie

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class Specie(
    @SerializedName("base_happiness")
    val baseHappiness: Int,
    @SerializedName("capture_rate")
    val captureRate: Int,
    @SerializedName("color")
    val color: JsonObject,
    @SerializedName("egg_groups")
    val eggGroups: JsonArray,
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: JsonArray,
    @SerializedName("forms_switchable")
    val formsSwitchable: Boolean,
    @SerializedName("gender_rate")
    val genderRate: Int,
    @SerializedName("genera")
    val genera: JsonArray,
    @SerializedName("generation")
    val generation: JsonObject,
    @SerializedName("growth_rate")
    val growthRate: JsonObject,
    @SerializedName("habitat")
    val habitat: JsonElement?,
    @SerializedName("has_gender_differences")
    val hasGenderDifferences: Boolean,
    @SerializedName("hatch_counter")
    val hatchCounter: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_baby")
    val isBaby: Boolean,
    @SerializedName("is_legendary")
    val isLegendary: Boolean,
    @SerializedName("is_mythical")
    val isMythical: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: Int,
)
