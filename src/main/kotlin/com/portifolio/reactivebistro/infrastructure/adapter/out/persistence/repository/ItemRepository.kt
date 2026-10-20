package com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.repository

import com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.entity.MenuItemEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface ItemRepository : ReactiveCrudRepository <MenuItemEntity, Long>