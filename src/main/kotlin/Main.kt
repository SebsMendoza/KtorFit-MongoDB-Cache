import db.MongoDbManager
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import models.Results
import org.litote.kmongo.getCollection
import repositories.RickYMortyRepository

fun main() = runBlocking {
    val repository = RickYMortyRepository()
    limpiarDatos()

    val todos = repository.findAll().take(10)
    todos.collect { value -> println(value) }
    do {
        println("Introduce un ID (numero) de personaje:")
        val id = readln().toInt()
        val oneCharacter = repository.findById(id)
        if (oneCharacter != null) {
            println(oneCharacter)
        } else {
            System.err.println("Personaje con id $id no existe")
        }
    } while (true)
}

private fun limpiarDatos() {
    if (MongoDbManager.database.getCollection<Results>().countDocuments() > 0) {
        MongoDbManager.database.getCollection<Results>().drop()
    }
}