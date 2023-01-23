package models

import kotlinx.serialization.Serializable

@Serializable
data class Personaje(
    val results: List<Results>? = null
)