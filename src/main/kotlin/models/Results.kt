package models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId

@Serializable
data class Results(
    val created: String,
    val gender: String,
    @BsonId
    val id: Int,
    val image: String,
    val name: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)