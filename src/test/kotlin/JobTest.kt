import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

class JobTest {

    @Test
    fun autoStartJobTest() {
        runBlocking {
            val job = CoroutineScope(Dispatchers.Default).launch {
                delay(1000)
                println("Coroutine autoStartJobTest Done! ${Thread.currentThread().name}")
            }
            job.join()
        }
    }

    @Test
    fun manualStartJobTest() {
        runBlocking {
            val job = CoroutineScope(Dispatchers.Default).launch(start = CoroutineStart.LAZY) {
                delay(1000)
                println("Coroutine manualStartJobTest Done! ${Thread.currentThread().name}")
            }
            job.start()
            job.join()
        }
    }

    @Test
    fun cancelJobTest() {
        runBlocking {
            val job = CoroutineScope(Dispatchers.Default).launch {
                delay(1000)
                println("Coroutine cancelJobTest Done! ${Thread.currentThread().name}")
            }
            job.cancel()
            job.join()
        }
    }

    @Test
    fun joinAllJobTest() {
        runBlocking {
            val job1 = CoroutineScope(Dispatchers.Default).launch {
                delay(1000)
                println("Coroutine job#1 Done! ${Thread.currentThread().name}")
            }
            val job2 = CoroutineScope(Dispatchers.Default).launch {
                delay(2000)
                println("Coroutine job#2 Done! ${Thread.currentThread().name}")
            }
            val job3 = CoroutineScope(Dispatchers.Default).launch {
                delay(3000)
                println("Coroutine job#3 Done! ${Thread.currentThread().name}")
            }
            joinAll(job1, job2, job3)
        }
    }
}