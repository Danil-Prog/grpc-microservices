package com.mobile.api

import com.mobile.api.grpc.UserGrpcImplementation
import io.grpc.ServerBuilder
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class GrpcServer(
    private val userGrpcImplementation: UserGrpcImplementation
) {

    @EventListener(ApplicationReadyEvent::class)
    fun grpcServer() {
        val server = ServerBuilder
            .forPort(15001)
            .addService(userGrpcImplementation)
            .build()
        Runtime.getRuntime().addShutdownHook(
            Thread {
                server.shutdown()
                server.awaitTermination()
            }
        )
        server.start()
        server.awaitTermination()
    }
}