package com.example.details.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.util.AppError
import com.example.core.util.Resource
import com.example.core.util.UiText
import com.example.details.domain.model.UserDetails
import com.example.details.domain.model.toUserDetails
import com.example.details.domain.usecase.GetUserDetailsUseCase
import com.example.helper.ResponseUtil
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito

class UserDetailsViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val mockDetailsUseCase: GetUserDetailsUseCase = Mockito.mock(GetUserDetailsUseCase::class.java)

    private lateinit var viewModel: UserDetailsViewModel

    private val scheduler = TestCoroutineScheduler()
    private val dispatcher = StandardTestDispatcher(scheduler)
    private val testCoroutineScope = TestScope(dispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = UserDetailsViewModel(mockDetailsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `calling getUserDetails update page state`() = runBlocking {
        val userDetails = ResponseUtil.getMockUserDetailsResponse("user_details_response.json")
            .toUserDetails()

        val userUiState = Resource.Success(data = userDetails)

        Mockito.`when`(mockDetailsUseCase.getUserDetails(anyString()))
            .thenReturn(flowOf(userUiState))

        assertThat(viewModel.state.value.isLoading).isFalse()
        assertThat(viewModel.state.value).isNotEqualTo(userUiState)

        viewModel.getUserDetails("user")
        scheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.isLoading).isFalse()
        assertThat(viewModel.state.value.userDetails?.userName).isEqualTo(userDetails.userName)
    }

    @Test
    fun `error on getUserDetails update page state`() = runBlocking {
        val uiText = UiText.DynamicString("generic error")
        val appError = AppError.GenericError(uiText)
        val userUiState = Resource.Error<UserDetails>(appError)

        Mockito.`when`(mockDetailsUseCase.getUserDetails(anyString()))
            .thenReturn(flowOf(userUiState))

        assertThat(viewModel.state.value.isLoading).isFalse()
        assertThat(viewModel.state.value.userDetails).isNull()

        viewModel.getUserDetails("user")
        scheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.isLoading).isFalse()
        assertThat(viewModel.state.value.userDetails).isNull()
        assertThat(viewModel.state.value.error).isNotNull()
        assertThat(viewModel.state.value.error).isEqualTo(uiText)
    }
}
