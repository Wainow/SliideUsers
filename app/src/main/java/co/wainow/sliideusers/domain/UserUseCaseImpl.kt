package co.wainow.sliideusers.domain

import co.wainow.sliideusers.domain.entity.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserUseCaseImpl @Inject constructor(
    private val repository: UserRepository,
) : UserUseCase {

    override suspend fun getUsersFromLastPage(): Flow<List<User>> {
        return repository.getUsersFromLastPage()
    }

    override suspend fun addUser(user: User) {
        repository.addUser(user)
    }

    override suspend fun removeUser(id: Long) {
        repository.removeUser(id)
    }
}