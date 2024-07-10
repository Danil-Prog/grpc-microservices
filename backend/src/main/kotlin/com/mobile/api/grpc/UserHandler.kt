package com.mobile.api.grpc

import com.mobile.api.configuration.DefaultGrpcInterceptor
import com.mobile.api.model.user.User
import com.mobile.api.service.user.CreateUserRequest
import com.mobile.api.service.user.CreateUserResponse
import com.mobile.api.service.user.UserServiceGrpcKt
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService(interceptors = [DefaultGrpcInterceptor::class])
class UserHandler : UserServiceGrpcKt.UserServiceCoroutineImplBase() {

    override suspend fun createUser(request: CreateUserRequest): CreateUserResponse {
        val user = User.newBuilder().setUsername("John").setPassword("#password").build()
        return CreateUserResponse.newBuilder().setUser(user).build()
    }
}