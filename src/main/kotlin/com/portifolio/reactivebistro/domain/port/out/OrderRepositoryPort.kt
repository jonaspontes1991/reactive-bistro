package com.portifolio.reactivebistro.domain.port.out

import com.portifolio.reactivebistro.domain.model.Order
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface OrderRepositoryPort {

    fun findAllOrderByTableNumber(tableNumber: Long): Flux<Order>
    fun save(order: Order): Mono<Order>
    fun delete(order: Order): Mono<Void>
}