import dev.cirosanchez.dev.cirosanchez.aqua.connection.AquaConnectionManager
import dev.cirosanchez.dev.cirosanchez.aqua.execution.AquaExecutor
import dev.cirosanchez.dev.cirosanchez.aqua.repository.AquaRepository
import kotlinx.coroutines.runBlocking
import util.PlayerData
import util.PlayerDataMapper
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AquaRepositoryTest {
    private val connection = AquaConnectionManager("mongodb://root:password@localhost:27017", "aquatest")
    private val executor = AquaExecutor()
    private val mapper = PlayerDataMapper()
    private val repository =
        AquaRepository(
            collectionName = "players",
            connectionManager = connection,
            executor = executor,
            documentMapper = mapper
        )

    @Test
    fun `insert and findById should work`() =
        runBlocking {

            val player =
                PlayerData(
                    id = "repo-test",
                    coins = 500
                )

            repository.insert(player)

            val result =
                repository.findById("repo-test")

            assertNotNull(result)

            assertEquals(
                player,
                result
            )
        }
}