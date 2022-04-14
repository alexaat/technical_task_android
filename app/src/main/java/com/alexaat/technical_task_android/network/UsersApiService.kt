package com.alexaat.technical_task_android.network

import com.alexaat.technical_task_android.model.User
import retrofit2.Response
import retrofit2.http.*

const val BASE_URL = "https://gorest.co.in/public/v2/"
const val page = 1

interface UsersApiService{

    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("access-token") token: String
    ):List<User>

    @POST("users")
    suspend fun postUser(
        @Query("access-token") token: String,
        @Body user: User
    ): Response<User>

    @DELETE("users/{id}")
    suspend fun deleteUser(
        @Path("id") id: Int,
        @Query("access-token") token: String
    ): Response<User>
}
