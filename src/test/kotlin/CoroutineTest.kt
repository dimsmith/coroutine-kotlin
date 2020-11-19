import org.junit.jupiter.api.Test
import kotlinx.coroutines.*
import org.junit.jupiter.api.assertThrows
import java.util.*
import kotlin.concurrent.thread

class CoroutineTest {

    @Test
    fun suspendTest() {
        runBlocking {
            helloCoroutine()
        }
    }

    private suspend fun helloCoroutine() {
        println("Coroutine ${Date()}")
        delay(1000)
        println("Kotlin ${Date()}")
    }

    @Test
    fun coroutineTest() {
        repeat(10_000) {
            CoroutineScope(Dispatchers.Default).launch {
                delay(1000)
                println("Done $it : ${Date()} ${Thread.currentThread().name} ")
            }

        }

        runBlocking {
            delay(5000)
        }
    }
}