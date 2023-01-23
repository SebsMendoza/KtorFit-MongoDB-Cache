package db

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoDatabase
import mu.KotlinLogging
import org.litote.kmongo.KMongo
import org.litote.kmongo.service.MongoClientProviderService

private val logger = KotlinLogging.logger { }

object MongoDbManager {
    private lateinit var mongoClient: MongoClient
    lateinit var database: MongoDatabase

    init {
        logger.debug { "Iniciando conexión a MongoDB" }
        mongoClient = KMongo.createClient("mongodb://mongoadmin:mongopass@localhost/RyMdatabase?authSource=admin")
        database = mongoClient.getDatabase("RyMdatabase")
    }
}