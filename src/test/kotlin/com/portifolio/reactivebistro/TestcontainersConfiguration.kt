package com.portifolio.reactivebistro

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.GenericContainer
import org.testcontainers.kafka.KafkaContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    fun kafkaContainer(): KafkaContainer {
        return KafkaContainer(DockerImageName.parse("apache/kafka-native:latest"))
    }

    @Bean
    @ServiceConnection(name = "redis")
    fun redisContainer(): GenericContainer<*> {
        return GenericContainer(DockerImageName.parse("redis:latest")).withExposedPorts(6379)
    }

}
