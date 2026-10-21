package com.portifolio.reactivebistro.domain.port.out

import com.portifolio.reactivebistro.domain.model.MenuItem
import com.portifolio.reactivebistro.domain.model.User
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface MenuItemRepositoryPort {
    fun findAll(): Flux<MenuItem>
    fun save(user: User, menuItem: MenuItem): Mono<MenuItem>
    fun deleteAll(): Mono<Void>
    fun delete(menuItem: MenuItem): Mono<Void>
}