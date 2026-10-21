package com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.repository.impl

import com.portifolio.reactivebistro.domain.model.User
import com.portifolio.reactivebistro.domain.port.out.UserRepositoryPort
import com.portifolio.reactivebistro.domain.utils.UserRole
import com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.entity.UserEntity
import com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.repository.UserRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class UserRepositoryAdapter(private val repository: UserRepository) : UserRepositoryPort {

    override fun findById(userId: Long): Mono<User> =
        repository.findById(userId)
            .map(UserEntity::toDomain)

    override fun findAllByRole(role: UserRole): Flux<User> =
        repository.findAllByRole(role)
            .map(UserEntity::toDomain)

    override fun save(user: User): Mono<User> =
        repository.save(UserEntity.from(user))
            .map(UserEntity::toDomain)

    override fun deleteById(userId: Long): Mono<Void> =
        repository.deleteById(userId)
}
