package repositories

import Service.ktorfit.KtorFitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.withContext
import models.Personaje
import models.Results
import mu.KotlinLogging

private val logger = KotlinLogging.logger { }

class RickYMortyRepository : CrudRepository<Personaje, Int> {

    private val client by lazy { KtorFitClient.instance }

    // private var listCharacters = mutableListOf<Results>()
//    private var listCharactersById = mutableListOf<RickYMortyPersonaje>()

    override suspend fun findAll(): Flow<Results> = withContext(Dispatchers.IO) {
        val call = client.getAll()
        return@withContext call.results!!.asFlow()
    }

    override fun delete(entity: Personaje): Boolean {
        TODO("Not yet implemented")
    }

    override fun update(entity: Personaje): Personaje {
        TODO("Not yet implemented")
    }

    override fun save(entity: Personaje): Personaje {
        TODO("Not yet implemented")
    }

    override suspend fun findById(id: Int): Personaje? {
        val call = client.getById(id)
        val nuevo = Personaje(
            listOf(
                Results(
                    created = call.created,
                    gender = call.gender,
                    id = call.id,
                    image = call.image,
                    name = call.name,
                    species = call.species,
                    status = call.status,
                    type = call.type,
                    url = call.url
                )
            )
        )
        return nuevo
    }


}