package com.mobile.api.configuration

import io.grpc.Context
import io.grpc.Contexts
import io.grpc.ForwardingServerCallListener.SimpleForwardingServerCallListener
import io.grpc.Metadata
import io.grpc.ServerCall
import io.grpc.ServerCallHandler
import io.grpc.ServerInterceptor
import org.apache.logging.log4j.CloseableThreadContext
import org.springframework.context.annotation.Configuration
import java.util.UUID

@Configuration
class DefaultGrpcInterceptor : ServerInterceptor {

    override fun <ReqT : Any?, RespT : Any?> interceptCall(
        call: ServerCall<ReqT, RespT>?,
        data: Metadata?,
        hanler: ServerCallHandler<ReqT, RespT>?
    ): ServerCall.Listener<ReqT> {
        val context = Context.current()
        val requestId = UUID.randomUUID().toString()

        return Contexts.interceptCall(context, call, data) { _, headers ->
            object : SimpleForwardingServerCallListener<ReqT>(hanler?.startCall(call, headers)) {

                override fun onHalfClose() {
                    CloseableThreadContext.put("requestID", requestId)
                        .use { super.onHalfClose() }
                }
            }
        }
    }
}