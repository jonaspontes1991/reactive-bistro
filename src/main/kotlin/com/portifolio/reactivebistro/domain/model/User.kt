package com.portifolio.reactivebistro.domain.model

import com.portifolio.reactivebistro.domain.utils.UserRole

data class User (
    val id: Long,
    val name: String,
    val username: String,
    val email: String,
    val password: String,
    val role: UserRole
)