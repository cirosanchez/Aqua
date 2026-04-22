package dev.cirosanchez.dev.cirosanchez.aqua.serialization

import org.bson.Document

/**
 * An interface to be implemented in kotlin objects that are to be stored in MongoDB.
 */
interface AquaDocumentMapper<T> {
    /**
     * Method to convert the kotlin object with data type T to Bson Document
     * @param obj the kotlin object to be converted
     * @return The document with the data from the kotlin object.
     */
    fun toDocument(obj: T): Document

    /**
     * Method to convert from Bson document to object with data type T
     * @param doc the incoming document to be converted.
     * @return The kotlin object represented in the document.
     */
    fun fromDocument(doc: Document): T
}