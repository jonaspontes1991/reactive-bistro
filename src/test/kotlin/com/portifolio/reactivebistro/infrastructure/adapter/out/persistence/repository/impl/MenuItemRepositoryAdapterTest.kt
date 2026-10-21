package com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.repository.impl

import com.portifolio.reactivebistro.domain.model.MenuItem
import com.portifolio.reactivebistro.domain.model.User
import com.portifolio.reactivebistro.domain.utils.UserRole
import com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.entity.MenuItemEntity
import com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.repository.MenuItemRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.Mockito.`when`
import org.springframework.security.access.AccessDeniedException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class MenuItemRepositoryAdapterTest {

    private lateinit var repository: MenuItemRepository
    private lateinit var menuItemRepository: MenuItemRepositoryAdapter

    private val menuItem = MenuItem(1L, "Risotto", "Mushroom risotto", 59.9, "MAIN")
    private val entity = MenuItemEntity.from(menuItem)

    @BeforeEach
    fun setUp() {
        repository = mock(MenuItemRepository::class.java)
        menuItemRepository = MenuItemRepositoryAdapter(repository)
    }

    @Test
    fun `findAll should return all mapped menu items`() {
        val other = menuItem.copy(id = 2L, title = "Tiramisu", category = "DESSERT")
        `when`(repository.findAll()).thenReturn(Flux.just(entity, MenuItemEntity.from(other)))

        StepVerifier.create(menuItemRepository.findAll())
            .expectNext(menuItem, other)
            .verifyComplete()
    }

    @ParameterizedTest
    @EnumSource(value = UserRole::class, names = ["MANAGER", "ADMIN"])
    fun `save should persist menu item when user is allowed`(role: UserRole) {
        `when`(repository.save(entity)).thenReturn(Mono.just(entity))

        StepVerifier.create(menuItemRepository.save(user(role), menuItem))
            .expectNext(menuItem)
            .verifyComplete()
    }

    @ParameterizedTest
    @EnumSource(value = UserRole::class, names = ["CLIENT", "WAITER", "CHEF"])
    fun `save should fail with AccessDeniedException when user is not allowed`(role: UserRole) {
        StepVerifier.create(menuItemRepository.save(user(role), menuItem))
            .expectError(AccessDeniedException::class.java)
            .verify()

        verifyNoInteractions(repository)
    }

    @Test
    fun `deleteAll should delete every menu item`() {
        `when`(repository.deleteAll()).thenReturn(Mono.empty())

        StepVerifier.create(menuItemRepository.deleteAll())
            .verifyComplete()

        verify(repository).deleteAll()
    }

    @Test
    fun `delete should delete menu item by id`() {
        `when`(repository.deleteById(1L)).thenReturn(Mono.empty())

        StepVerifier.create(menuItemRepository.delete(menuItem))
            .verifyComplete()

        verify(repository).deleteById(1L)
    }

    private fun user(role: UserRole) = User(1L, "Jonas", "jonas", "jonas@bistro.com", "secret", role)
}
