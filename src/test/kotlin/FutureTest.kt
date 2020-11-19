import org.junit.jupiter.api.Test
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.Future
import kotlin.system.measureTimeMillis

class FutureTest {

    private fun fakeRequest() : String {
        Thread.sleep(1000)
        return "Result #1"
    }

    private fun fakeRequest2() : String {
        Thread.sleep(2000)
        return "Result #2"
    }

    @Test
    fun nonParallel() {
        val time = measureTimeMillis {
            val req1 = fakeRequest()
            val req2 = fakeRequest2()

            println(req1)
            println(req2)
        }
        println("Done. Time $time")
    }

    @Test
    fun parallel() {
        val executor = Executors.newFixedThreadPool(2)
        val time = measureTimeMillis {
            val req1 : Future<String> = executor.submit(Callable { fakeRequest() })
            val req2 : Future<String> = executor.submit(Callable { fakeRequest2() })

            println(req1.get())
            println(req2.get())
        }

        println("Done. Time $time")
    }
}