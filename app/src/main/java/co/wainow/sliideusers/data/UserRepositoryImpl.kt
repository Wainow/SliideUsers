package co.wainow.sliideusers.data

import co.wainow.sliideusers.data.api.ApiService
import co.wainow.sliideusers.domain.UserRepository
import co.wainow.sliideusers.domain.entity.User
import co.wainow.sliideusers.toModel
import co.wainow.sliideusers.toRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : UserRepository {

    override suspend fun getUsersFromLastPage(): Flow<List<User>> {
        return flow { emit(apiService.getUsers(page = LAST_PAGE_NUM, perPage = DEFAULT_USERS_AMOUNT).toModel()) }
    }

    override suspend fun addUser(user: User) {
        apiService.createUser(user.toRequest())
    }

    override suspend fun removeUser(id: Long) {
        apiService.deleteUser(id.toString())
    }

    companion object {

        const val LAST_PAGE_NUM = 1
        const val DEFAULT_USERS_AMOUNT = 20
    }
}