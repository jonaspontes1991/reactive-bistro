package com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.entity

data class MenuItemEntity(
    val id: Long,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
)