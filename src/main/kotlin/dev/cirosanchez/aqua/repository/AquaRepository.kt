package dev.cirosanchez.dev.cirosanchez.aqua.repository

import com.mongodb.client.model.Filters.eq
import dev.cirosanchez.dev.cirosanchez.aqua.connection.AquaConnectionManager
import dev.cirosanchez.dev.cirosanchez.aqua.execution.AquaExecutor
import dev.cirosanchez.dev.cirosanchez.aqua.serialization.AquaDocumentMapper

/**
 * The interaction class for library users.
 * @param T The kotlin object to be stored in Mongo.
 * @param collectionName The collection name to be used for interaction.
 * @param connectionManager The connection manager object to connect to Mongo.
 * @param executor The executor object with desired threads #.
 * @param documentMapper The document mapper of the desired kotlin object.
 */
class AquaRepository<T : Any>(
    private val collectionName: String,
    private val connectionManager: AquaConnectionManager,
    private val executor: AquaExecutor,
    private val documentMapper: AquaDocumentMapper<T>
) {

    companion object {

        /**
         * A static method to be used to create a new instance of AquaRepository, providing total customization over
         * the asynchronous execution features.
         * @param collectionName The collection to be used for the corresponding kotlin object.
         * @param connectionManager The connection manager for the repository.
         * @param executor The executor for async operations.
         * @param documentMapper The document mapper related to the repository.
         */
        fun <T: Any> create(collectionName: String,
                            connectionManager: AquaConnectionManager,
                            executor: AquaExecutor,
                            documentMapper: AquaDocumentMapper<T>): AquaRepository<T> {

            require(collectionName.isNotBlank()) {
                "Collection name cannot be blank."
            }

            requireNotNull(documentMapper) {
                "DocumentMapper cannot be null."
            }

            return AquaRepository(
                collectionName = collectionName,
                connectionManager = connectionManager,
                executor = executor,
                documentMapper = documentMapper
            )
        }
    }

    /**
     * The collection in MongoDB of the kotlin object.
     */
    private val collection = connectionManager.getCollection(collectionName)

    /**
     * Method to insert an object into MongoDB.
     * @param obj The kotlin object to be inserted.
     */
    suspend fun insert(obj: T) {
        executor.run {
            val document = documentMapper.toDocument(obj)

            collection.insertOne(document)
        }
    }

    /**
     * Method to fetch a kotlin object by its ID.
     * @param id The id of the kotlin object to fetch.
     */
    suspend fun findById(id: String): T? {
        return executor.run {
            val document =
                collection
                    .find(eq("_id", id))
                    .firstOrNull()

            document?.let { documentMapper.fromDocument(it) }
        }
    }

    /**
     * Method to update a kotlin object.
     * @param obj The id of the kotlin object to be updated.
     */
    suspend fun update(obj: T) {
        executor.run {
            val document = documentMapper.toDocument(obj)

            val id = document["_id"]

            collection.updateOne(eq("_id", id), document)
        }
    }

    /**
     * Method to delete a kotlin object by its ID.
     * @param id The id of the kotlin object to be deleted.
     */
    suspend fun deleteById(id: String) {
        executor.run({
            collection.deleteOne(eq("_id", id))
        })
    }

    /**
     * Method to check if an ID exists in the collection.
     * @param id The id of the kotlin object to be checked.
     */
    suspend fun exists(id: String): Boolean {
        return executor.run {
            collection.find(eq("_id", id))
                .firstOrNull() != null
        }
    }

    /**
     * A method to shut down the system.
     */
    fun shutdown() {
        executor.shutdown()
        connectionManager.close()
    }
}