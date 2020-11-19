import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TimeOutCoroutineTest {

    @Test
    fun timeoutTest() {
        assertThrows<TimeoutCancellationException> {
            runBlocking {
                val job = CoroutineScope(Dispatchers.Default).launch {
                    withTimeout(3_000) {
                        println("Coroutine is running...")
                        repeat(4) {
                            delay(1_000)
                            println("Coroutine $it is executed!")
                        }
                        println("Coroutine never done!")
                    }
                }
                job.join()
                job.invokeOnCompletion { cause ->
                    throw cause!!
                }
            }
            println("Never appear")
        }
    }

    @Test
    fun timeoutOrNullTest() {
        println("Start job!")
        var sum = 0
        runBlocking {
            val job = CoroutineScope(Dispatchers.Default).launch {
                withTimeoutOrNull(3_000) {
                    println("Coroutine is running...")
                    repeat(4) {
                        delay(1_000)
                        val deferred = async { calc(it) }
                        sum += deferred.await()
                    }
                    println("Coroutine is done!")
                }
            }
            job.join()
        }
        println("Job Done with null value!")
        println("Sum: $sum!")
    }

    private fun calc(num: Int): Int {
        return num * num
    }
}