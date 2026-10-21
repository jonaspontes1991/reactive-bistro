package com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.repository.impl

import com.portifolio.reactivebistro.domain.model.Order
import com.portifolio.reactivebistro.domain.utils.TableStatus
import com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.entity.OrderEntity
import com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.repository.OrderRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDateTime

class OrderRepositoryAdapterTest {

    private lateinit var repository: OrderRepository
    private lateinit var orderRepository: OrderRepositoryAdapter

    private val order = Order(
        id = 1L,
        tableNumber = 5L,
        waiterId = 10L,
        clientId = 20L,
        status = TableStatus.OCCUPIED,
        createdAt = LocalDateTime.of(2026, 10, 20, 20, 0),
    )
    private val entity = OrderEntity.from(order)

    @BeforeEach
    fun setUp() {
        repository = mock(OrderRepository::class.java)
        orderRepository = OrderRepositoryAdapter(repository)
    }

    @Test
    fun `findAllOrderByTableNumber should return mapped orders`() {
        val other = order.copy(id = 2L)
        `when`(repository.findAllByTableNumber(5L))
            .thenReturn(Flux.just(entity, OrderEntity.from(other)))

        StepVerifier.create(orderRepository.findAllOrderByTableNumber(5L))
            .expectNext(order, other)
            .verifyComplete()
    }

    @Test
    fun `findAllOrderByTableNumber should complete empty when table has no orders`() {
        `when`(repository.findAllByTableNumber(7L)).thenReturn(Flux.empty())

        StepVerifier.create(orderRepository.findAllOrderByTableNumber(7L))
            .verifyComplete()
    }

    @Test
    fun `save should persist entity and return domain order`() {
        `when`(repository.save(entity)).thenReturn(Mono.just(entity))

        StepVerifier.create(orderRepository.save(order))
            .expectNext(order)
            .verifyComplete()
    }

    @Test
    fun `delete should delete order by id`() {
        `when`(repository.deleteById(1L)).thenReturn(Mono.empty())

        StepVerifier.create(orderRepository.delete(order))
            .verifyComplete()

        verify(repository).deleteById(1L)
    }
}
