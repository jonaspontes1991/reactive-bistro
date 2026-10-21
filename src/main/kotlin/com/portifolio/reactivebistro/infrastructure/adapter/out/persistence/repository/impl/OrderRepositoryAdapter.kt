package com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.repository.impl

import com.portifolio.reactivebistro.domain.model.Order
import com.portifolio.reactivebistro.domain.port.out.OrderRepositoryPort
import com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.entity.OrderEntity
import com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.repository.OrderRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class OrderRepositoryAdapter(private val repository: OrderRepository) : OrderRepositoryPort {

    override fun findAllOrderByTableNumber(tableNumber: Long): Flux<Order> =
        repository.findAllByTableNumber(tableNumber)
            .map { it.toDomain() }

    override fun save(order: Order): Mono<Order> =
        repository.save(OrderEntity.from(order))
            .map { it.toDomain() }

    override fun delete(order: Order): Mono<Void> =
        repository.deleteById(order.id)
}
