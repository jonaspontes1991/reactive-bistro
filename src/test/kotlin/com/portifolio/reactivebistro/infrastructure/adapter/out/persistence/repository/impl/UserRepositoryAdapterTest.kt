package com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.repository.impl

import com.portifolio.reactivebistro.domain.model.User
import com.portifolio.reactivebistro.domain.utils.UserRole
import com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.entity.UserEntity
import com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.Mockito.`when`
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class UserRepositoryAdapterTest {

    private lateinit var repository: UserRepository
    private lateinit var userRepository: UserRepositoryAdapter

    private val user = User(1L, "Jonas", "jonas", "jonas@bistro.com", "secret", UserRole.WAITER)
    private val entity = UserEntity.from(user)

    @BeforeEach
    fun setUp() {
        repository = mock(UserRepository::class.java)
        userRepository = UserRepositoryAdapter(repository)
    }

    @Test
    fun `findById should return mapped user when found`() {
        `when`(repository.findById(1L)).thenReturn(Mono.just(entity))

        StepVerifier.create(userRepository.findById(1L))
            .expectNext(user)
            .verifyComplete()
    }

    @Test
    fun `findById should complete empty when user does not exist`() {
        `when`(repository.findById(99L)).thenReturn(Mono.empty())

        StepVerifier.create(userRepository.findById(99L))
            .verifyComplete()
    }

    @Test
    fun `findAllByRole should return all users with given role`() {
        val other = user.copy(id = 2L, username = "maria")
        `when`(repository.findAllByRole(UserRole.WAITER))
            .thenReturn(Flux.just(entity, UserEntity.from(other)))

        StepVerifier.create(userRepository.findAllByRole(UserRole.WAITER))
            .expectNext(user, other)
            .verifyComplete()
    }

    @Test
    fun `save should persist entity and return domain user`() {
        `when`(repository.save(entity)).thenReturn(Mono.just(entity))

        StepVerifier.create(userRepository.save(user))
            .expectNext(user)
            .verifyComplete()
    }

    @Test
    fun `deleteById should delete in a single call without querying the user first`() {
        `when`(repository.deleteById(1L)).thenReturn(Mono.empty())

        StepVerifier.create(userRepository.deleteById(1L))
            .verifyComplete()

        verify(repository).deleteById(1L)
        verifyNoMoreInteractions(repository)
    }
}
