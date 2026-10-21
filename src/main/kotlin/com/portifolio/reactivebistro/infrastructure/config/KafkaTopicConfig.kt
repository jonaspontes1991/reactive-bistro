package com.portifolio.reactivebistro.infrastructure.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration(proxyBeanMethods = false)
class KafkaTopicConfig {

    @Bean
    fun ordersTopic(): NewTopic =
        TopicBuilder.name(ORDERS_TOPIC)
            .partitions(1)
            .replicas(1)
            .build()

    companion object {
        const val ORDERS_TOPIC = "orders"
    }
}
