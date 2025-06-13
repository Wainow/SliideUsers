package co.wainow.sliideusers.data.api

import co.wainow.sliideusers.data.entity.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<UserDto>

    @POST("users")
    suspend fun createUser(
        @Body user: UserDto
    )

    @DELETE("users/{userId}")
    suspend fun deleteUser(
        @Path("userId") userId: String
    ): Response<Unit>
}