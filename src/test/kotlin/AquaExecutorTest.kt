import dev.cirosanchez.dev.cirosanchez.aqua.execution.AquaExecutor
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class AquaExecutorTest {

    @Test
    fun execute() {
        runBlocking {
            val executor = AquaExecutor()

            val result = executor.run {
                println(
                    Thread.currentThread().name
                )

                42
            }

            assertEquals(42, result)

            executor.shutdown()
        }
    }
}