package com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.entity

import com.portifolio.reactivebistro.domain.model.Order
import com.portifolio.reactivebistro.domain.utils.TableStatus
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("orders")
data class OrderEntity(
    @Id val id: Long,
    val tableNumber: Long,
    val waiterId: Long,
    val clientId: Long,
    val status: TableStatus,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(order: Order): OrderEntity = OrderEntity(
            id = order.id,
            tableNumber = order.tableNumber,
            waiterId = order.waiterId,
            clientId = order.clientId,
            status = order.status,
            createdAt = order.createdAt
        )
    }
    fun toDomain() = Order(id, tableNumber, waiterId, clientId, status, createdAt)
}
