package com.portifolio.reactivebistro.domain.port.out

import com.portifolio.reactivebistro.domain.model.User
import com.portifolio.reactivebistro.domain.utils.UserRole
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserRepositoryPort {


    fun findById(userId: Long): Mono<User>
    fun findAllByRole(role: UserRole): Flux<User>
    fun save(user: User): Mono<User>
    fun deleteById(userId: Long): Mono<Void>

}