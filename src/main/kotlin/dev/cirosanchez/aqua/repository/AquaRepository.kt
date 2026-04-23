package dev.cirosanchez.dev.cirosanchez.aqua.repository

import com.mongodb.client.model.Filters.eq
import dev.cirosanchez.dev.cirosanchez.aqua.connection.AquaConnectionManager
import dev.cirosanchez.dev.cirosanchez.aqua.execution.AquaExecutor
import dev.cirosanchez.dev.cirosanchez.aqua.serialization.AquaDocumentMapper

class AquaRepository<T : Any>(
    private val collectionName: String,
    private val connectionManager: AquaConnectionManager,
    private val executor: AquaExecutor,
    private val documentMapper: AquaDocumentMapper<T>
) {

    private val collection = connectionManager.getCollection(collectionName)

    suspend fun insert(obj: T) {
        executor.run {
            val document = documentMapper.toDocument(obj)

            collection.insertOne(document)
        }
    }

    suspend fun findById(id: String): T? {
        return executor.run {
            val document =
                collection
                    .find(eq("_id", id))
                    .firstOrNull()

            document?.let { documentMapper.fromDocument(it) }
        }
    }

    suspend fun update(obj: T) {
        executor.run {
            val document = documentMapper.toDocument(obj)

            val id = document["_id"]

            collection.updateOne(eq("_id", id), document)
        }
    }

    suspend fun deleteById(id: String) {
        executor.run({
            collection.deleteOne(eq("_id", id))
        })
    }

    suspend fun exists(id: String): Boolean {
        return executor.run {
            collection.find(eq("_id", id))
                .firstOrNull() != null
        }
    }

    fun shutdown() {
        executor.shutdown()
        connectionManager.close()
    }
}