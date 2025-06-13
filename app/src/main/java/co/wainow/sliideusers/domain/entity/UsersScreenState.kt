package co.wainow.sliideusers.domain.entity

import co.wainow.sliideusers.presentation.entity.UserUI

sealed class UsersScreenState {
    data class Loading(val message: String = "") : UsersScreenState()
    data class Success(val users: List<UserUI>) : UsersScreenState()
    data class Error(val error: Throwable) : UsersScreenState() {
        val message get() = error.message.toString()
    }
}