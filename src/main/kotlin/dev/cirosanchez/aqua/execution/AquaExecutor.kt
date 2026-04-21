package dev.cirosanchez.dev.cirosanchez.aqua.execution

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

/**
 * The class in charge of Async Execution.
 * @param threadCount the amount of threads to use for execution. (default = 4)
 */
class AquaExecutor(
    threadCount: Int = 4
) {
    /**
     * The dispatcher is the entry point for async execution with Kotlinx.
     */
    private val dispatcher = Executors.newFixedThreadPool(threadCount).asCoroutineDispatcher()


    /**
     * A block function that'll execute the code via the dispatcher.
     * @param block such block of code to execute asynchronously.
     */
    suspend fun <T> run(
        block: () -> T
    ): T {

        return withContext(dispatcher) {
            block()
        }
    }

    /**
     * Function to close the dispatcher.
     */
    fun shutdown() {
        dispatcher.close()
    }
}