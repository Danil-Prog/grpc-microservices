package com.mobile.api.configuration

import com.mobile.api.configuration.annotation.GrpcImplementation
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import kotlin.reflect.KClass
import kotlin.reflect.full.hasAnnotation

@Component
class GrpcImplementationPostProcessor : BeanPostProcessor {

    private val beansName = mutableListOf<KClass<out Any>>()

    /**
     * Регистрирует классы, реализующие grpc функции.
     */
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (bean::class.hasAnnotation<GrpcImplementation>()) {
            logger.info("Register class annotated ${GrpcImplementation::class.simpleName} " +
                "with name ${bean::class.simpleName}")
            beansName.add(bean::class)
        }

        return bean
    }

    companion object {
        private val logger = LoggerFactory.getLogger(GrpcImplementationPostProcessor::class.java)
    }
}