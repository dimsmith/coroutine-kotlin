import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class SuspendCoroutineTest {
    companion object {
        const val RESUME = 1
        const val RESUME_WITH = 2
    }

    @Throws(Exception::class)
    private suspend fun requestFakeApi(expected: Int): String = suspendCoroutine { continuation ->
        when (expected) {
            RESUME -> continuation.resume("Response from fakeAPI")
            RESUME_WITH -> continuation.resumeWith(Result.success("Response Result from fakeAPI"))
            else -> continuation.resumeWithException(Exception("Undefined of expected!"))
        }

    }

    @Test
    fun resumeTest() {
        runBlocking {
            val job = CoroutineScope(Dispatchers.IO).launch {
                println("START: ${Date()}")
                delay(1000)
                val result = requestFakeApi(RESUME)
                println("Result: $result")
            }
            job.join()
            println("FINISH: ${Date()}")
        }
    }

    @Test
    fun resumeWithTest() {
        runBlocking {
            val job = CoroutineScope(Dispatchers.IO).launch {
                println("START: ${Date()}")
                delay(1000)
                val result = requestFakeApi(RESUME_WITH)
                println("Result: $result")
            }
            job.join()
            println("FINISH: ${Date()}")
        }
    }

    @Test
    fun resumeWithExceptionTest() {
        runBlocking {
            val job = CoroutineScope(Dispatchers.IO).launch {
                println("START: ${Date()}")
                delay(1000)
                try {
                    requestFakeApi(12)
                } catch (e: Exception) {
                    // throw to job
                    throw CancellationException(e.localizedMessage)
                }
            }
            job.join()

            // handle exception
            job.invokeOnCompletion { error ->
                if (error != null) {
                    println("RESULT ERROR: ${error.message}")
                }
                println("FINISH: ${Date()}")
            }
        }
    }
}