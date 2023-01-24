import Service.cache.UserCache
import db.MongoDbManager
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.runBlocking
import models.User
import mu.KotlinLogging
import org.litote.kmongo.getCollection
import repositories.Repository

private val cache = UserCache()
fun main() = runBlocking {
    val repository = Repository(cache)
    limpiarDatos()

    val todos = repository.findAll()
    todos.collect { value -> println(value) }

    do {
        println("Introduce un ID (numero) de usuario:")
        val id = readln().toInt()
        val oneCharacter = repository.findById(id)
        println(oneCharacter)
        if (oneCharacter != null) {
            repository.save(oneCharacter)
        }
        if (oneCharacter != null) {
            repository.delete(oneCharacter)
        }
        repository.findAll().distinctUntilChanged().collect{ users -> println(users)}
    } while (true)
}

//Limpia MongoDB
private fun limpiarDatos() {
    if (MongoDbManager.database.getCollection<User>().countDocuments() > 0) {
        MongoDbManager.database.getCollection<User>().drop()
    }
}