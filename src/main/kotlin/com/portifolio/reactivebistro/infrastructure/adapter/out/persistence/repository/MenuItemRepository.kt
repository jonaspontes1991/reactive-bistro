package com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.repository

import com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.entity.MenuItemEntity
import org.springframework.data.r2dbc.repository.R2dbcRepository

interface MenuItemRepository : R2dbcRepository<MenuItemEntity, Long>
