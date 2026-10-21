package com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.repository.impl

import com.portifolio.reactivebistro.domain.model.Table
import com.portifolio.reactivebistro.domain.port.out.TableRepositoryPort
import com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.entity.TableEntity
import com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.repository.OrderRepository
import com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.repository.TableRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class TableRepositoryAdapter(
    private val repository: TableRepository,
    private val orderRepository: OrderRepository,
) : TableRepositoryPort {

    override fun findByTableId(tableNumber: Long): Mono<Table> =
        repository.findByTableNumber(tableNumber)
            .map { it.toDomain() }

    override fun finAllTablesByUserId(userId: Long): Flux<Table> =
        orderRepository.findAllByWaiterIdOrClientId(userId, userId)
            .map { it.tableNumber }
            .distinct()
            .flatMap { repository.findByTableNumber(it) }
            .map { it.toDomain() }

    override fun save(table: Table): Mono<Table> =
        repository.findByTableNumber(table.tableNumber)
            .map { TableEntity.from(table, it.id) }
            .defaultIfEmpty(TableEntity.from(table))
            .flatMap { repository.save(it) }
            .map { it.toDomain() }

    override fun delete(table: Table): Mono<Void> =
        repository.findByTableNumber(table.tableNumber)
            .flatMap { repository.delete(it) }
}
