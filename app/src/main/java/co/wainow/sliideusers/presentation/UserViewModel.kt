package co.wainow.sliideusers.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.wainow.sliideusers.domain.UserUseCase
import co.wainow.sliideusers.domain.entity.User
import co.wainow.sliideusers.domain.entity.UsersScreenState
import co.wainow.sliideusers.presentation.entity.Gender
import co.wainow.sliideusers.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _screenState =
        MutableStateFlow<UsersScreenState>(UsersScreenState.Loading())

    val screenState get() = _screenState

    fun refresh() = loadUsers()

    init {
        loadUsers()
    }

    fun loadUsers() {
        _screenState.value = UsersScreenState.Loading()
        viewModelScope.launch {
            try {
                userUseCase.getUsersFromLastPage().collect { users ->
                    _screenState.value = UsersScreenState.Success(users.toUi())
                }
            } catch (e: Exception) {
                _screenState.value = UsersScreenState.Error(e)
            }
        }
    }

    fun addUser(name: String, email: String, gender: Gender) {
        viewModelScope.launch {
            try {
                val newUser = User(
                    id = 0,
                    name = name,
                    email = email,
                    gender = gender.toString().lowercase(),
                    isActive = false
                )
                userUseCase.addUser(newUser)
                loadUsers()
            } catch (e: Exception) {
                _screenState.value = UsersScreenState.Error(e)
            }
        }
    }

    fun removeUser(id: Long) {
        viewModelScope.launch {
            try {
                userUseCase.removeUser(id)
                loadUsers()
            } catch (e: Exception) {
                _screenState.value = UsersScreenState.Error(e)
            }
        }
    }
}