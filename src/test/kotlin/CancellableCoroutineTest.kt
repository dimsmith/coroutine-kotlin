import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

class CancellableCoroutineTest {

    @Test
    fun notCancellableTest() {
        runBlocking {
            val job = CoroutineScope(Dispatchers.Default).launch {
                println("Start coroutine")
                Thread.sleep(2000)
                println("End coroutine")
            }
            job.cancelAndJoin()
        }
    }

    @Test
    fun cancellableTest() {
        runBlocking {
            val job = CoroutineScope(Dispatchers.Default).launch {
                println("Start coroutine")
                delay(2000)
                ensureActive()
                println("End coroutine")
            }
            job.cancelAndJoin()
            job.invokeOnCompletion { cause: Throwable? ->
                if (cause != null) println("Cancelled: ${cause.localizedMessage}")
                else println("Result ${job}")
            }
        }
    }

    @Test
    fun cancellableWithCancellationExceptionTest() {
        runBlocking {
            val job = CoroutineScope(Dispatchers.Default).launch {
                ensureActive()
                println("Start coroutine")
                delay(3000)
                ensureActive()
                println("End coroutine")
            }
            job.cancel(CancellationException("Cancelled by user"))
            job.join()
            job.invokeOnCompletion { cause: Throwable? ->
                if (cause != null) println("Cancelled: ${cause.localizedMessage}")
                else println("Result ${job}")
            }
        }
    }

    @Test
    fun cancellableWithTryCatchTest() {
        runBlocking {
            val job = CoroutineScope(Dispatchers.Default).launch {
                try {
                    ensureActive()
                    println("Start coroutine")
                    delay(3000)
                    ensureActive()
                    println("End coroutine")
                } catch (e: CancellationException) {
                    println("Throw: ${e.localizedMessage}")
                } finally {
                    println("Finally next step...")
                }
            }
            job.cancel(CancellationException("Cancelled by user"))
            job.join()
        }
    }
}