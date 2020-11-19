import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.concurrent.thread


@DisplayName("Thread Test")
class ThreadTest {

    private fun log(msg: String) {
        println("Thread: [${Thread.currentThread().name}] :: $msg")
    }


    @Test
    fun testThread() {
        thread(true) {
            Thread.sleep(1000)
            log(Date().toString())
        }
        Thread.sleep(2000)
    }

    @Test
    fun multipleThread() {
        thread(true) {
            Thread.sleep(1000)
            log(Date().toString())
            log("Thread pertama")
        }
        thread(true) {
            Thread.sleep(1000)
            log(Date().toString())
            log("Thread kedua")
        }
        Thread.sleep(2000)
    }
}