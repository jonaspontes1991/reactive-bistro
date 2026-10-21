package com.portifolio.reactivebistro.domain.model

import com.portifolio.reactivebistro.domain.utils.TableStatus
import org.springframework.data.annotation.Id
import java.time.LocalDateTime

data class Order(
    val id: Long,
    val tableNumber: Long,
    val waiterId: Long,
    val clientId: Long,
    val status: TableStatus,
    val createdAt: LocalDateTime,
)