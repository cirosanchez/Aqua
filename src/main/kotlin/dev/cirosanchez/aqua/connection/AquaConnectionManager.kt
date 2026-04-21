package dev.cirosanchez.dev.cirosanchez.aqua.connection

import com.mongodb.kotlin.client.MongoClient
import com.mongodb.kotlin.client.MongoDatabase
import org.bson.Document

/**
 * Handles all connection-related code with MongoDB server.
 * @param connectionString the connection string provided for connection to remote.
 * @param databaseName the desired database name for usage.
 */
class AquaConnectionManager(private val connectionString: String, private val databaseName: String) {

    // TODO: [FOR RELEASE] remove this testing variable
    val name: String
        get() = databaseName

    /**
     * MongoClient instance for connection, initialized by lazy.
     */
    private val mongoClient: MongoClient by lazy {
        MongoClient.create(connectionString)
    }

    /**
     * MongoDatabase instance for connection, initialized by lazy.
     */
    private val mongoDatabase: MongoDatabase by lazy {
        mongoClient.getDatabase(databaseName)
    }

    /**
     * Method provided to access a collection safely.
     * @param name the name of the collection to be retrieved
     * @return the MongoCollection<Document> object of the specified collection.
     */
    fun getCollection(name: String) = mongoDatabase.getCollection<Document>(name)

    /**
     * Method provided to close the connection with the remote.
     */
    fun close() = mongoClient.close()
}