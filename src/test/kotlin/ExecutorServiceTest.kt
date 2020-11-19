import org.junit.jupiter.api.Test
import java.util.*
import java.util.concurrent.Executors

class ExecutorServiceTest {

    @Test
    fun testSingleThreadPool() {
        val executorService = Executors.newSingleThreadExecutor()
        repeat(10) {
            val thread = Runnable {
                Thread.sleep(1000)
                println("Thread-$it ${Thread.currentThread().name} ${Date()}")
            }
            executorService.execute(thread)
        }
        Thread.sleep(11_000)
    }

    @Test
    fun testFixThreadPool() {
        val executorService = Executors.newFixedThreadPool(3)
        repeat(10) {
            val thread = Runnable {
                Thread.sleep(1000)
                println("Thread-$it ${Thread.currentThread().name} ${Date()}")
            }
            executorService.execute(thread)
        }
        Thread.sleep(5000)
    }

    @Test
    fun testCacheThreadPool() {
        val executorService = Executors.newCachedThreadPool()
        repeat(10) {
            val thread = Runnable {
                Thread.sleep(1000)
                println("Thread-$it ${Thread.currentThread().name} ${Date()}")
            }
            executorService.execute(thread)
        }
        Thread.sleep(5000)
    }
}