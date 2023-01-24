package models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val first_name: String,
    val last_name: String,
    val email: String? = null,
    val avatar: String? = null,
)