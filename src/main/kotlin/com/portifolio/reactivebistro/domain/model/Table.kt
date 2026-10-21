package com.portifolio.reactivebistro.domain.model

import com.portifolio.reactivebistro.domain.utils.TableStatus
import java.time.LocalDateTime

data class Table(
    val tableNumber: Long,
    val status: TableStatus,
    val createdAt: LocalDateTime,
)