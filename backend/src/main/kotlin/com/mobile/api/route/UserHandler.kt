package com.mobile.api.route

import com.mobile.api.service.user.UserService
import net.devh.boot.grpc.server.service.GrpcService
import proto.mobile.api.service.user.CreateUserRequest
import proto.mobile.api.service.user.CreateUserResponse
import proto.mobile.api.service.user.DeleteUserByIdRequest
import proto.mobile.api.service.user.DeleteUserByIdResponse
import proto.mobile.api.service.user.UpdateUserRequest
import proto.mobile.api.service.user.UpdateUserResponse
import proto.mobile.api.service.user.UserServiceGrpcKt.UserServiceCoroutineImplBase
import proto.mobile.api.service.user.createUserResponse
import proto.mobile.api.service.user.deleteUserByIdResponse
import proto.mobile.api.service.user.updateUserResponse

@GrpcService
class UserHandler(
    private val userService: UserService,
) : UserServiceCoroutineImplBase() {

    override suspend fun createUser(request: CreateUserRequest): CreateUserResponse {
        return createUserResponse {
            this.user = userService.createUser(request)
        }
    }

    override suspend fun updateUser(request: UpdateUserRequest): UpdateUserResponse {
        return updateUserResponse {
            this.user = userService.updateUser(request)
        }
    }

    override suspend fun deleteUserById(request: DeleteUserByIdRequest): DeleteUserByIdResponse {
        return deleteUserByIdResponse {
            userService.deleteUserById(request)
        }
    }
}