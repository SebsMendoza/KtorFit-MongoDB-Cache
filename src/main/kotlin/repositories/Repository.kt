package repositories

import Service.cache.UserCache
import Service.ktorfit.KtorFitClient
import db.MongoDbManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import models.User
import mu.KotlinLogging
import org.litote.kmongo.deleteOneById
import org.litote.kmongo.findOneById
import org.litote.kmongo.getCollection
import org.litote.kmongo.save

private val logger = KotlinLogging.logger { }

class Repository(private val userCache: UserCache) : CrudRepository<User, Int> {
    private val client by lazy { KtorFitClient.instance }
    private var refreshJob: Job? = null
    private var listaUsuarios = mutableListOf<User>()

    init {
        refreshCache()
    }


    private fun refreshCache() {
        if (refreshJob != null) refreshJob?.cancel()
        refreshJob = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                logger.debug { "Comprobando cache..." }
                if (listaUsuarios.isNotEmpty()) {
                    listaUsuarios.forEach {
                        userCache.cache.put(it.id, it)
                    }
                    listaUsuarios.clear()
                    logger.debug { "Cache actualizada: ${userCache.cache.asMap().size}" }
                    delay(userCache.refreshTime.toLong())
                } else {
                    logger.debug { "Cache actual: ${userCache.cache.asMap().size}" }
                    delay(userCache.refreshTime.toLong())
                }
            }
        }
    }

    override suspend fun findAll(): Flow<User> = withContext(Dispatchers.IO) {
        logger.debug { "findAll()" }
        var resultados = userCache.cache.asMap().values.asFlow()
        if (resultados.toList().isEmpty()) {
            logger.debug { "Usuario no encontrado en la cache... Buscando en MongoDB" }
            resultados = MongoDbManager.database.getCollection<User>().find().asFlow()
        }
        if (resultados.toList().isEmpty()) {
            logger.debug { "Buscando usuario en la api..." }
            resultados = client.getAll().data!!.asFlow()
        }
        logger.debug { "Usuario encontrado" }
        resultados
    }

    override suspend fun delete(entity: User): Boolean = withContext(Dispatchers.IO) {
        logger.debug { "delete($entity)" }
        var existe = false
        val usuario = userCache.cache.asMap()[entity.id]
        if (usuario != null) {
            listaUsuarios.removeIf { it.id == usuario.id }
            userCache.cache.invalidate(entity.id)
            MongoDbManager.database.getCollection<User>().deleteOneById(entity.id)
            existe = true
        } else if (MongoDbManager.database.getCollection<User>().deleteOneById(entity.id).equals(entity)) {
            existe = true
        }
        existe
    }

    override suspend fun save(entity: User): User = withContext(Dispatchers.IO) {
        logger.debug { "save($entity)" }
        listaUsuarios.add(entity)
        MongoDbManager.database.getCollection<User>().save(entity)
        entity
    }

    override suspend fun findById(id: Int): User? = withContext(Dispatchers.IO) {
        logger.debug { "findById($id)" }
        var usuario: User? = null
        userCache.cache.asMap().forEach { if (it.key == id) usuario = it.value }
        usuario ?: MongoDbManager.database.getCollection<User>().findOneById(id) ?: client.getById(id).data
    }

}

