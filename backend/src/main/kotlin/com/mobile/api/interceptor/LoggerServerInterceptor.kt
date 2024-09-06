package com.mobile.api.interceptor

import io.grpc.Context
import io.grpc.Contexts
import io.grpc.ForwardingServerCallListener.SimpleForwardingServerCallListener
import io.grpc.Metadata
import io.grpc.ServerCall
import io.grpc.ServerCallHandler
import io.grpc.ServerInterceptor
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor
import org.apache.logging.log4j.CloseableThreadContext
import org.slf4j.LoggerFactory
import java.util.UUID

@GrpcGlobalServerInterceptor
class LoggerServerInterceptor : ServerInterceptor {

    /**
     * Логгирует результат выполнения RPC-вызовов.
     * Формирует requestId, передавая в контекст.
     */
    override fun <ReqT : Any?, RespT : Any?> interceptCall(
        call: ServerCall<ReqT, RespT>?,
        next: Metadata?,
        hanler: ServerCallHandler<ReqT, RespT>?
    ): ServerCall.Listener<ReqT> {
        val requestId = UUID.randomUUID().toString()
        val context = Context.current().withValue(Context.key("requestId"), requestId)

        logger.info("Call ID: $requestId")

        return Contexts.interceptCall(context, call, next) { _, headers ->
            object : SimpleForwardingServerCallListener<ReqT>(hanler?.startCall(call, headers)) {

                override fun onHalfClose() {
                    CloseableThreadContext.put("requestID", requestId)
                        .use { super.onHalfClose() }
                }

                override fun onCancel() {
                    logger.error("Request with id: $requestId has been cancelled")
                }
            }
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(LoggerServerInterceptor::class.java)
    }
}
