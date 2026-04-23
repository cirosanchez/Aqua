import org.bson.Document
import util.PlayerData
import util.PlayerDataMapper
import kotlin.test.Test
import kotlin.test.assertEquals


class AquaDocumentMapperTest {

    private val mapper =
        PlayerDataMapper()

    @Test
    fun `toDocument should map to PlayerData`() {
        val player =
            PlayerData(
                id = "player1",
                coins = 150
            )

        val document =
            mapper.toDocument(player)

        assertEquals(
            "player1",
            document.getString("_id")
        )

        assertEquals(
            150,
            document.getInteger("coins")
        )
    }

    @Test
    fun `fromDocument should map from PlayerData`() {
        val document =
            Document()
                .append("_id", "player1")
                .append("coins", 150)

        val player =
            mapper.fromDocument(document)

        assertEquals(
            "player1",
            player.id
        )

        assertEquals(
            150,
            player.coins
        )
    }
}