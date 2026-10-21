package com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.repository.impl

import com.portifolio.reactivebistro.domain.model.MenuItem
import com.portifolio.reactivebistro.domain.model.User
import com.portifolio.reactivebistro.domain.port.out.MenuItemRepositoryPort
import com.portifolio.reactivebistro.domain.utils.UserRole
import com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.entity.MenuItemEntity
import com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.repository.MenuItemRepository
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
data class MenuItemRepositoryAdapter(val repository: MenuItemRepository) : MenuItemRepositoryPort {

    override fun findAll(): Flux<MenuItem> =
        repository.findAll()
            .map { it.toDomain() }

    override fun save(user: User, menuItem: MenuItem): Mono<MenuItem> {
        if (user.role !in setOf(UserRole.MANAGER, UserRole.ADMIN)) {
            return Mono.error(AccessDeniedException("User ${user.id} is not allowed to manage menu items"))
        }
        return repository.save(MenuItemEntity.from(menuItem))
            .map { it.toDomain() }
    }

    override fun deleteAll(): Mono<Void> =
        repository.deleteAll()

    override fun delete(menuItem: MenuItem): Mono<Void> =
        repository.deleteById(menuItem.id)
}
