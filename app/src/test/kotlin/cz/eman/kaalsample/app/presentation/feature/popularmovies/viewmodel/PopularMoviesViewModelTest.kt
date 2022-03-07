package cz.eman.kaalsample.app.presentation.feature.popularmovies.viewmodel

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.viewModelScope
import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaal.domain.result.Result
import cz.eman.kaalsample.domain.feature.movies.popular.usecase.GetPopularMoviesUseCase
import cz.eman.kaalsample.presentation.feature.popularmovies.states.PopularMoviesViewStates
import cz.eman.kaalsample.presentation.feature.popularmovies.viewmodel.PopularMoviesViewModel
import io.kotlintest.TestCase
import io.kotlintest.TestResult
import io.kotlintest.matchers.types.shouldBeInstanceOf
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.spyk
import io.mockk.unmockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

/**
 *  @author stefan.toth@eman.cz
 */
//@InternalCoroutinesApi
class PopularMoviesViewModelTest : StringSpec() {

    private val states = mutableListOf<PopularMoviesViewStates>()

    lateinit var viewModel: PopularMoviesViewModel

    lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    override fun beforeTest(testCase: TestCase) {
        fakeRunningOnMainThread()
        Dispatchers.setMain(mainThreadSurrogate)
        mockViewModelDependencies()
        this.viewModel = spyk(PopularMoviesViewModel(getPopularMoviesUseCase))

        mockkStatic("androidx.lifecycle.ViewModelKt")
        this.viewModel.viewState.observeForever { state ->
            states.add(state)
        }
    }

    override fun afterTest(testCase: TestCase, result: TestResult) {
        super.afterTest(testCase, result)
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()

        unmockkStatic("androidx.lifecycle.ViewModelKt")
    }

    private fun fakeRunningOnMainThread() {
        ArchTaskExecutor.getInstance()
            .setDelegate(object : TaskExecutor() {
                override fun executeOnDiskIO(runnable: Runnable) = runnable.run()

                override fun postToMainThread(runnable: Runnable) = runnable.run()

                override fun isMainThread(): Boolean = true
            })

        this.viewModel = PopularMoviesViewModel(mockk())
    }

    private fun mockViewModelDependencies() {
        this.getPopularMoviesUseCase = mockk()
    }

    /**
     * The tests
     */
    init {
        "viewmodel should exist"{
            viewModel shouldNotBe null
        }

        "state should be NotInitialized on start"{
            viewModel.viewState.value shouldBe PopularMoviesViewStates.NotInitialized
        }

        "state should be Loading after call the loading"{
            runTest {
                coEvery { getPopularMoviesUseCase(Unit) } returns Result.Success(listOf())
                viewModel.loadPopularMovies()
                viewModel.viewState.value shouldBe PopularMoviesViewStates.Loading
            }
        }

        "get popular movies use case should called when loading"{
            runBlocking {
                every { viewModel.viewModelScope } returns this
                coEvery { getPopularMoviesUseCase(Unit) } returns Result.Success(listOf())
                viewModel.loadPopularMovies()
                delay(BE_SURE_TIME)
                coVerify(exactly = 1) { getPopularMoviesUseCase(Unit) }
            }
        }

        "state should be Movies Loaded after the loading in case of success"{
            coEvery { getPopularMoviesUseCase(Unit) } returns Result.Success(listOf())
            runBlocking {
                every { viewModel.viewModelScope } returns this
                viewModel.loadPopularMovies()
                delay(BE_SURE_TIME)
            }
            val state = viewModel.viewState.value
            state.shouldBeInstanceOf<PopularMoviesViewStates.MoviesLoaded>()
        }

        "state should be Error after fail the loading"{
            coEvery { getPopularMoviesUseCase(Unit) } returns Result.Error(ErrorResult())
            runBlocking {
                every { viewModel.viewModelScope } returns this
                viewModel.loadPopularMovies()
                delay(BE_SURE_TIME)
            }

            val state = viewModel.viewState.value
            state.shouldBeInstanceOf<PopularMoviesViewStates.LoadingError>()
        }

        "state should be Invalid after viewModel invalidated"{
            viewModel.invalidate()
            viewModel.viewState.value shouldBe PopularMoviesViewStates.Invalid
        }

    }

    val BE_SURE_TIME = 100L
}
