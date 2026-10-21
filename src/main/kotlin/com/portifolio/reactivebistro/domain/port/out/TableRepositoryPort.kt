package com.portifolio.reactivebistro.domain.port.out

import com.portifolio.reactivebistro.domain.model.Table
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface TableRepositoryPort {

    fun findByTableId(tableNumber: Long): Mono<Table>
    fun finAllTablesByUserId(userId: Long): Flux<Table>
    fun save(table: Table): Mono<Table>
    fun delete(table: Table): Mono<Void>
}