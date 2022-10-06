package com.littlewind.jetflix.domain.interactors

import com.littlewind.jetflix.domain.repository.MovieRepository
import com.littlewind.testutils.MockkUnitTest
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetGenresUseCaseTest : MockkUnitTest() {
    @RelaxedMockK
    lateinit var movieRepository: MovieRepository

    @SpyK
    @InjectMockKs
    private lateinit var getGenresUseCase: GetGenresUseCase

    @Test
    fun `when invoke executeSync function in GetGenresUseCase then MovieRepository is called`() =
        runTest {

            // Act (When)
            getGenresUseCase.executeSync(Unit)

            // Assert (Then)
            coVerify { movieRepository.fetchGenres() }
        }

    @Test
    fun `when collect last element from GetGenresUseCase then MovieRepository is called`() =
        runTest {

            // Act (When)
            val result = getGenresUseCase(Unit).lastOrNull()
            print("phungtd: ---$result---")
            // Assert (Then)
            coVerify { movieRepository.fetchGenres() }
        }
}
