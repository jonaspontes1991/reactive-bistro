package com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.entity

import com.portifolio.reactivebistro.domain.model.User
import com.portifolio.reactivebistro.domain.utils.UserRole
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("users")
data class UserEntity (
    @Id val id: Long,
    val name: String,
    val username: String,
    val email: String,
    val password: String,
    val role: UserRole
){
    companion object {
        fun from(user: User): UserEntity = UserEntity(
            id = user.id,
            name = user.name,
            username = user.username,
            email = user.email,
            password = user.password,
            role = user.role
        )
    }
    fun toDomain() = User(id, name, username, email, password, role)
}