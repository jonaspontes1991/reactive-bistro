package com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.entity

import com.portifolio.reactivebistro.domain.model.MenuItem
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("menu_items")
data class MenuItemEntity(
    @Id val id: Long,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
) {
    companion object {
        fun from(menuItem: MenuItem): MenuItemEntity = MenuItemEntity(
            id = menuItem.id,
            title = menuItem.title,
            description = menuItem.description,
            price = menuItem.price,
            category = menuItem.category
        )
    }
    fun toDomain() = MenuItem(id, title, description, price, category)
}
