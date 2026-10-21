package com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.repository

import com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.entity.OrderEntity
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Flux

interface OrderRepository : R2dbcRepository<OrderEntity, Long> {
    fun findAllByTableNumber(tableNumber: Long): Flux<OrderEntity>
    fun findAllByWaiterIdOrClientId(waiterId: Long, clientId: Long): Flux<OrderEntity>
}
