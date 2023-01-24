package db

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoDatabase
import mu.KotlinLogging
import org.litote.kmongo.KMongo

private val logger = KotlinLogging.logger { }

object MongoDbManager {
    private var mongoClient: MongoClient
    var database: MongoDatabase

    init {
        logger.debug { "Iniciando conexión a MongoDB" }
        mongoClient = KMongo.createClient("mongodb://mongoadmin:mongopass@localhost/Usuarios?authSource=admin")
        database = mongoClient.getDatabase("Usuarios")
    }
}