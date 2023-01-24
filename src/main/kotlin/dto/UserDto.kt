package dto

import kotlinx.serialization.Serializable
import models.User

@Serializable
data class AllUserDto(
    val data: List<User>? = null
)

@Serializable
data class OneUserDto(
    val data: User? = null
)

@Serializable
data class CreateDto(
    val id: Int,
    val first_name: String,
    val last_name: String,
    val email: String? = null,
    val avatar: String? = null
)

@Serializable
data class UpdateDto(
    var id: Int,
    var first_name: String,
    var last_name: String,
    var email: String?,
    var avatar: String?
)

