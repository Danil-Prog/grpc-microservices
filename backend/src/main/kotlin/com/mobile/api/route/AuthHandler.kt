package com.mobile.api.route

import com.mobile.api.configuration.annotation.GrpcImplementation
import com.mobile.api.service.auth.AuthService
import net.devh.boot.grpc.server.service.GrpcService
import proto.mobile.api.service.auth.AuthServiceGrpcKt.AuthServiceCoroutineImplBase
import proto.mobile.api.service.auth.LoginRequest
import proto.mobile.api.service.auth.LoginResponse
import proto.mobile.api.service.auth.RegistrationRequest
import proto.mobile.api.service.auth.RegistrationResponse
import proto.mobile.api.service.auth.loginResponse

@GrpcService
@GrpcImplementation
class AuthHandler(
    private val authService: AuthService,
): AuthServiceCoroutineImplBase() {

    override suspend fun login(request: LoginRequest): LoginResponse {
        return loginResponse {
            authService.login(request)
        }
    }

    override suspend fun registration(request: RegistrationRequest): RegistrationResponse {
        return super.registration(request)
    }
}