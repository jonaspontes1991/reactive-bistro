package com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.repository


import com.portifolio.reactivebistro.domain.utils.UserRole
import com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.entity.UserEntity
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Flux

interface UserRepository : R2dbcRepository<UserEntity, Long> {
    fun findAllByRole(role: UserRole): Flux<UserEntity>
}
