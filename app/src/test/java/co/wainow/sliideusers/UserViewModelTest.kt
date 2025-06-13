package co.wainow.sliideusers

import app.cash.turbine.test
import co.wainow.sliideusers.domain.UserUseCase
import co.wainow.sliideusers.domain.entity.User
import co.wainow.sliideusers.domain.entity.UsersScreenState
import co.wainow.sliideusers.presentation.UserViewModel
import co.wainow.sliideusers.presentation.entity.Gender
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.*
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.argThat
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var userUseCase: UserUseCase
    private lateinit var viewModel: UserViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        userUseCase = mock()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadUsers emits Success when users are fetched`() = runTest {
        val users = listOf(
            User(1, "name name", "name@example.com", "male", true),
            User(2, "name name", "name@example.com", "female", false)
        )
        whenever(userUseCase.getUsersFromLastPage()).thenReturn(flowOf(users))

        viewModel = UserViewModel(userUseCase)

        viewModel.screenState.test {
            assertTrue(awaitItem() is UsersScreenState.Loading)

            val successState = awaitItem()
            assertTrue(successState is UsersScreenState.Success)
            assertEquals(users.size, (successState as UsersScreenState.Success).users.size)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadUsers emits Error when exception is thrown`() = runTest {
        val error = RuntimeException("Failed to load")
        whenever(userUseCase.getUsersFromLastPage()).thenReturn(flow { throw error })

        viewModel = UserViewModel(userUseCase)

        viewModel.screenState.test {
            assertTrue(awaitItem() is UsersScreenState.Loading)

            val errorState = awaitItem()
            assertTrue(errorState is UsersScreenState.Error)
            assertEquals(error, (errorState as UsersScreenState.Error).error)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `addUser calls useCase and reloads users`() = runTest {
        whenever(userUseCase.getUsersFromLastPage()).thenReturn(flowOf(emptyList()))

        viewModel = UserViewModel(userUseCase)
        advanceUntilIdle()
        viewModel.addUser("New User", "new@example.com", Gender.Male)
        advanceUntilIdle()

        verify(userUseCase).addUser(argThat {
            name == "New User" && email == "new@example.com" && gender == "male"
        })
        verify(userUseCase, atLeastOnce()).getUsersFromLastPage()
    }

    @Test
    fun `removeUser calls useCase and reloads users`() = runTest {
        whenever(userUseCase.getUsersFromLastPage()).thenReturn(flowOf(emptyList()))
        whenever(userUseCase.removeUser(any())).thenReturn(Unit)

        viewModel = UserViewModel(userUseCase)

        viewModel.removeUser(123L)
        advanceUntilIdle()

        verify(userUseCase).removeUser(123L)
        verify(userUseCase, atLeastOnce()).getUsersFromLastPage()
    }
}
