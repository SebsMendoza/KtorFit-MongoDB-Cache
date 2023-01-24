package Service.ktorfit

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.create
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object KtorFitClient {
    private const val API_URL = "https://reqres.in/api/"

    private val ktorFit by lazy {
        Ktorfit.Builder().httpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        isLenient = true
                        ignoreUnknownKeys = true
                        prettyPrint = true
                    }
                )
            }
            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
            .baseUrl(API_URL)
            .build()
    }

    val instance by lazy {
        ktorFit.create<KtorFitRest>()
    }
}