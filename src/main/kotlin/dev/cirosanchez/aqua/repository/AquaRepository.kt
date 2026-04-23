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
}