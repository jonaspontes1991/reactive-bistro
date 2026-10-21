package com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.repository.impl

import com.portifolio.reactivebistro.domain.model.Table
import com.portifolio.reactivebistro.domain.utils.TableStatus
import com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.entity.OrderEntity
import com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.entity.TableEntity
import com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.repository.OrderRepository
import com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.repository.TableRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.Mockito.`when`
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDateTime

class TableRepositoryAdapterTest {

    private lateinit var repository: TableRepository
    private lateinit var orderRepository: OrderRepository
    private lateinit var tableRepository: TableRepositoryAdapter

    private val createdAt = LocalDateTime.of(2026, 10, 20, 20, 0)
    private val table = Table(tableNumber = 5L, status = TableStatus.AVAILABLE, createdAt = createdAt)
    private val entity = TableEntity.from(table, id = 100L)

    @BeforeEach
    fun setUp() {
        repository = mock(TableRepository::class.java)
        orderRepository = mock(OrderRepository::class.java)
        tableRepository = TableRepositoryAdapter(repository, orderRepository)
    }

    @Test
    fun `findByTableId should return mapped table`() {
        `when`(repository.findByTableNumber(5L)).thenReturn(Mono.just(entity))

        StepVerifier.create(tableRepository.findByTableId(5L))
            .expectNext(table)
            .verifyComplete()
    }

    @Test
    fun `findByTableId should complete empty when table does not exist`() {
        `when`(repository.findByTableNumber(9L)).thenReturn(Mono.empty())

        StepVerifier.create(tableRepository.findByTableId(9L))
            .verifyComplete()
    }

    @Test
    fun `finAllTablesByUserId should return distinct tables from user orders`() {
        val otherTable = Table(tableNumber = 6L, status = TableStatus.OCCUPIED, createdAt = createdAt)
        `when`(orderRepository.findAllByWaiterIdOrClientId(10L, 10L)).thenReturn(
            Flux.just(order(1L, tableNumber = 5L), order(2L, tableNumber = 5L), order(3L, tableNumber = 6L))
        )
        `when`(repository.findByTableNumber(5L)).thenReturn(Mono.just(entity))
        `when`(repository.findByTableNumber(6L)).thenReturn(Mono.just(TableEntity.from(otherTable, id = 101L)))

        StepVerifier.create(tableRepository.finAllTablesByUserId(10L))
            .expectNext(table, otherTable)
            .verifyComplete()

        verify(repository, times(1)).findByTableNumber(5L)
        verify(repository, times(1)).findByTableNumber(6L)
    }

    @Test
    fun `finAllTablesByUserId should complete empty when user has no orders`() {
        `when`(orderRepository.findAllByWaiterIdOrClientId(10L, 10L)).thenReturn(Flux.empty())

        StepVerifier.create(tableRepository.finAllTablesByUserId(10L))
            .verifyComplete()
    }

    @Test
    fun `save should update existing table keeping its id`() {
        val updated = table.copy(status = TableStatus.OCCUPIED)
        val updatedEntity = TableEntity.from(updated, id = 100L)
        `when`(repository.findByTableNumber(5L)).thenReturn(Mono.just(entity))
        `when`(repository.save(updatedEntity)).thenReturn(Mono.just(updatedEntity))

        StepVerifier.create(tableRepository.save(updated))
            .expectNext(updated)
            .verifyComplete()

        verify(repository).save(updatedEntity)
    }

    @Test
    fun `save should insert new table when it does not exist`() {
        val newEntity = TableEntity.from(table)
        `when`(repository.findByTableNumber(5L)).thenReturn(Mono.empty())
        `when`(repository.save(newEntity)).thenReturn(Mono.just(newEntity.copy(id = 100L)))

        StepVerifier.create(tableRepository.save(table))
            .expectNext(table)
            .verifyComplete()

        verify(repository).save(newEntity)
    }

    @Test
    fun `delete should remove existing table`() {
        `when`(repository.findByTableNumber(5L)).thenReturn(Mono.just(entity))
        `when`(repository.delete(entity)).thenReturn(Mono.empty())

        StepVerifier.create(tableRepository.delete(table))
            .verifyComplete()

        verify(repository).delete(entity)
    }

    @Test
    fun `delete should do nothing when table does not exist`() {
        `when`(repository.findByTableNumber(5L)).thenReturn(Mono.empty())

        StepVerifier.create(tableRepository.delete(table))
            .verifyComplete()

        verify(repository).findByTableNumber(5L)
        verifyNoMoreInteractions(repository)
    }

    private fun order(id: Long, tableNumber: Long) = OrderEntity(
        id = id,
        tableNumber = tableNumber,
        waiterId = 10L,
        clientId = 20L,
        status = TableStatus.OCCUPIED,
        createdAt = createdAt,
    )
}
