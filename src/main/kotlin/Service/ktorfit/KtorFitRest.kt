package Service.ktorfit

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import models.Personaje
import models.Results

interface KtorFitRest {
    @GET("character")
    suspend fun getAll(): Personaje

    @GET("character/{id}")
    suspend fun getById(@Path("id") id: Int): Results
}