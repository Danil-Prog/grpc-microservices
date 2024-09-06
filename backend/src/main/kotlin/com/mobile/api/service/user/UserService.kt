package com.mobile.api.service.user

import com.mobile.api.model.User
import com.mobile.api.repository.UserRepository
import com.mobile.api.validate.UserValidate
import org.springframework.stereotype.Service
import proto.mobile.api.service.user.CreateUserRequest
import proto.mobile.api.service.user.DeleteUserByIdRequest
import proto.mobile.api.service.user.UpdateUserRequest
import proto.mobile.api.model.user.User as UserProto

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userValidate: UserValidate,
) {

    fun createUser(request: CreateUserRequest): UserProto {
        val user = User.fromProtobuf(request.user)
        userValidate.validate(user)
        val savedUser = userRepository.save(user)

        return User.toProtobuf(savedUser)
    }

    fun updateUser(request: UpdateUserRequest): UserProto {
        TODO()
    }

    fun deleteUserById(request: DeleteUserByIdRequest): UserProto {
        TODO()
    }
}
