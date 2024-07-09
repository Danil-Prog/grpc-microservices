package com.mobile.api.grpc

import com.mobile.api.model.user.User
import com.mobile.api.service.user.CreateUserRequest
import com.mobile.api.service.user.CreateUserResponse
import com.mobile.api.service.user.UserServiceGrpc
import io.grpc.stub.StreamObserver
import org.springframework.stereotype.Component

@Component
class UserGrpcImplementation : UserServiceGrpc.UserServiceImplBase() {

    override fun createUser(request: CreateUserRequest?, responseObserver: StreamObserver<CreateUserResponse>?) {
        println("start method createUser")
        val user = User
            .newBuilder()
            .setUsername("username")
            .setPassword("password")
            .build()
        println("CreateUser request on successfully")

        responseObserver?.onNext(CreateUserResponse.newBuilder().setUser(user).build())
        responseObserver?.onCompleted()
    }
}