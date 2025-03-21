package com.example.powerverse.data

import com.angel.powerverse.R
import com.google.gson.annotations.SerializedName

class SuperheroResponse (
    val response: String,
    val results: List<Superhero>
)

class Superhero (
    val id: String,
    val name: String,
    val biography: Biography,
    val work: Work,
    val appearance: Appearance,
    val image: Image,
    val powerstats:Powerstats
)
{
    fun getAlignmentColor():Int {
        return when (biography.alignment){
            "good" -> R.color.good
            "bad" -> R.color.bad
            "neutral" -> R.color.neutral
            else -> {R.color.black}
        }
    }
}

class Biography (
    val publisher: String,
    @SerializedName("full-name") val realName: String,
    @SerializedName("place-of-birth") val placeOfBirth: String,
    val alignment: String
)

class Work (
    val occupation: String,
    val base: String
)

class Appearance (
    val gender: String,
    val race: String,
    @SerializedName("eye-color") val eyeColor: String,
    @SerializedName("hair-color") val hairColor: String,
    val height: List<String>,
    val weight: List<String>,
) {
    fun getWeightKg(): String {
        return weight[1]
    }

    fun getHeightCm(): String {
        return height[1]
    }
}
class Image (val url: String)
{
}

class Powerstats (
    val intelligence: String,
    val strength: String,
    val speed: String,
    val durability: String,
    val power: String,
    val combat: String
)