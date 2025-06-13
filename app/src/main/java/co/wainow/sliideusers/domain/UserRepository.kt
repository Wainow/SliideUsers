package co.wainow.sliideusers.domain

import co.wainow.sliideusers.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUsersFromLastPage(): Flow<List<User>>

    suspend fun addUser(user: User)

    suspend fun removeUser(id: Long)
}