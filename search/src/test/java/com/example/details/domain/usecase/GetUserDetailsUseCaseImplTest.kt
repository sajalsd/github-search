package com.example.details.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.util.AppError
import com.example.core.util.Resource
import com.example.details.data.repository.UserDetailsRepository
import com.example.details.domain.model.UserDetails
import com.example.helper.ResponseUtil
import com.example.network.data.model.ApiResponse
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import java.io.IOException

class GetUserDetailsUseCaseImplTest {
    private val userDetailsRepository = Mockito.mock(UserDetailsRepository::class.java)

    private lateinit var useCase: GetUserDetailsUseCase

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private val scheduler = TestCoroutineScheduler()
    private val dispatcher = StandardTestDispatcher(scheduler)
    private val testScope = TestScope(dispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        useCase = GetUserDetailsUseCaseImpl(userDetailsRepository)
    }

    @Test
    fun `getUserDetails returns userDetails on success`() = runTest {
        val userDetailsDto = ResponseUtil.getMockUserDetailsResponse("user_details_response.json")

        Mockito.`when`(userDetailsRepository.getUserDetails("user"))
            .thenReturn(ApiResponse.Success(userDetailsDto))

        testScope.launch {
            useCase.getUserDetails("user").collectLatest {
                Truth.assertThat(it).isInstanceOf(Resource.Loading::class.java)
                Mockito.verify(userDetailsRepository).getUserDetails("user")
                Truth.assertThat(it).isInstanceOf(Resource::class.java)
                Truth.assertThat(it).isInstanceOf(Resource.Success::class.java)
                (it as Resource.Success).data.run {
                    Truth.assertThat(it).isInstanceOf(UserDetails::class.java)
                }
            }
        }
    }

    @Test
    fun `getUserDetails returns and Map Error`() = runTest {
        val response = ApiResponse.Error(IOException("android.net.error"))

        Mockito.`when`(userDetailsRepository.getUserDetails("user"))
            .thenReturn(response)

        testScope.launch {
            useCase.getUserDetails("user").collectLatest {
                Truth.assertThat(it).isInstanceOf(Resource::class.java)
                Truth.assertThat(it).isInstanceOf(Resource.Error::class.java)
                (it as Resource.Error).data.run {
                    Truth.assertThat(it).isNotInstanceOf(IOException::class.java)
                    Truth.assertThat(it).isInstanceOf(AppError::class.java)
                }
            }
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
