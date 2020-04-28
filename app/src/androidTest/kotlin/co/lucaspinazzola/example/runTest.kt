package co.lucaspinazzola.example

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Rule

@ExperimentalCoroutinesApi
private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

@ExperimentalCoroutinesApi
/*actual */fun <T> runTest(block: suspend () -> T) {
    Dispatchers.setMain(dispatcher)
    runBlocking { block() }
    Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
    dispatcher.cleanupTestCoroutines()
}

/*actual */typealias Rule = Rule




/*actual */typealias InstantTaskExecutorRule = InstantTaskExecutorRule


