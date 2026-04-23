package util

import dev.cirosanchez.dev.cirosanchez.aqua.serialization.AquaDocumentMapper
import org.bson.Document

class PlayerDataMapper : AquaDocumentMapper<PlayerData> {
    override fun toDocument(obj: PlayerData): Document {
        return Document().append("_id", obj.id).append("coins", obj.coins)
    }

    override fun fromDocument(doc: Document): PlayerData {
        return PlayerData(doc.getString("_id"), doc.getInteger("coins"))
    }
}