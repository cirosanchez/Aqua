import dev.cirosanchez.dev.cirosanchez.aqua.connection.AquaConnectionManager
import org.bson.Document
import kotlin.test.Test
import kotlin.test.assertNotNull

class AquaConnectionManagerTest {

    @Test
    fun testConnectionAndInsert() {

        val connection =
            AquaConnectionManager(
                "mongodb://root:password@localhost:27017",
                "aquatest"
            )

        val collection =
            connection.getCollection("players")

        val doc =
            Document()
                .append("_id", "junit-test")
                .append("coins", 200)

        collection.insertOne(doc)

        val found =
            collection
                .find(Document("_id", "junit-test"))
                .first()

        assertNotNull(found)

        connection.close()
    }
}