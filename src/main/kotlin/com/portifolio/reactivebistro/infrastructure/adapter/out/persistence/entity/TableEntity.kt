package com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.entity

import com.portifolio.reactivebistro.domain.utils.TableStatus
import java.time.LocalDateTime

data class TableEntity(
    val id: Long,
    val tableNumber: Long,
    val status: TableStatus,
    val createdAt: LocalDateTime,
)