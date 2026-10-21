package com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.repository

import com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.entity.TableEntity
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Mono

interface TableRepository : R2dbcRepository<TableEntity, Long> {
    fun findByTableNumber(tableNumber: Long): Mono<TableEntity>
}
