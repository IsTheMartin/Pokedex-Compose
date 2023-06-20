package me.ismartin.pokedexcompose.data.remote.models.specie

import com.google.gson.annotations.SerializedName

data class Specie(
    @SerializedName("base_happiness")
    val baseHappiness: Int,
    @SerializedName("capture_rate")
    val captureRate: Int,
    @SerializedName("color")
    val color: Color,
    @SerializedName("egg_groups")
    val eggGroups: List<EggGroup>,
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntry>,
    @SerializedName("forms_switchable")
    val formsSwitchable: Boolean,
    @SerializedName("gender_rate")
    val genderRate: Int,
    @SerializedName("genera")
    val genera: List<Genera>,
    @SerializedName("generation")
    val generation: Generation,
    @SerializedName("growth_rate")
    val growthRate: GrowthRate,
    @SerializedName("habitat")
    val habitat: Habitat,
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
