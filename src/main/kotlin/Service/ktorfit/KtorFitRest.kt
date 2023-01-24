package Service.ktorfit

import de.jensklingenberg.ktorfit.http.*
import dto.AllUserDto
import dto.CreateDto
import dto.OneUserDto
import dto.UpdateDto
import models.User

interface KtorFitRest {
    @GET("users")
    suspend fun getAll(): AllUserDto

    @GET("users/{id}")
    suspend fun getById(@Path("id") id: Int): OneUserDto

    @POST("users")
    suspend fun create(@Body user: User): CreateDto

    @PUT("users/{id}")
    suspend fun update(@Path("id") id: Int, @Body user: User): UpdateDto

}