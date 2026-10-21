package com.portifolio.reactivebistro.domain.model

data class MenuItem(
    val id: Long,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
)