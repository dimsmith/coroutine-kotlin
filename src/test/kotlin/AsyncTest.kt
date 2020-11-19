import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class AsyncTest {

    private suspend fun foo(): Int {
        delay(1000)
        return 10
    }

    private suspend fun bar(): Int {
        delay(2000)
        return 10
    }

    @Test
    fun withOutConcurrency() {
        runBlocking {
            val time = measureTimeMillis {
                val foo = foo()
                val bar = bar()
                println("Result: ${foo + bar}")
            }
            println("Total time: $time") // expected time: > 3000 ms
        }
    }

    @Test
    fun withConcurrency() {
        runBlocking {
            val time = measureTimeMillis {
                val foo = CoroutineScope(Dispatchers.Default).launch { foo() }
                val bar = CoroutineScope(Dispatchers.Default).launch { bar() }
                joinAll(foo, bar)
            }
            println("Total time: $time") // expected time: < 2020 ms
        }
    }


    /**
     * @see FutureTest
     */
    @Test
    fun withAsync() {
        runBlocking {
            val time = measureTimeMillis {
                val job = CoroutineScope(Dispatchers.Default).launch {
                    val foo: Deferred<Int> = async { foo() }
                    val bar: Deferred<Int> = async { bar() }
                    println("Result: ${foo.await() + bar.await()}")
                }
                job.join()
            }
            println("Total time: $time") // expected time: < 3000 ms
        }
    }

    @Test
    fun withContextAsync() {
        runBlocking {
            val time = measureTimeMillis {
                val job = CoroutineScope(Dispatchers.Default).launch {
                    val foo = withContext(Dispatchers.Default) { foo() } // same as async { foo() }.await()
                    val bar = withContext(Dispatchers.Default) { bar() } // same as async { bar() }.await()
                    println("Result: ${foo + bar}")
                }
                job.join()
            }
            println("Total time: $time") // expected time: > 3000 ms
        }
    }

    @Test
    fun withAsyncAwaitAll() {
        runBlocking {
            val time = measureTimeMillis {
                val job = CoroutineScope(Dispatchers.Default).launch {
                    val foo = async { foo() }
                    val bar = async { bar() }
                    val foo1 = async { foo() }
                    val bar1 = async { bar() }
                    println("Result: ${awaitAll(foo, bar, foo1, bar1).sum()}")
                }
                job.join()
            }
            println("Total time: $time") // expected time: < 3000 ms
        }
    }
}