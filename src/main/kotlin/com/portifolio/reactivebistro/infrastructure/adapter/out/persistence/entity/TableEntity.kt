package com.portifolio.reactivebistro.infrastructure.adapter.out.persistence.entity

import com.portifolio.reactivebistro.domain.model.Table
import com.portifolio.reactivebistro.domain.utils.TableStatus
import org.springframework.data.annotation.Id
import java.time.LocalDateTime
import org.springframework.data.relational.core.mapping.Table as DbTable

@DbTable("tables")
data class TableEntity(
    @Id val id: Long,
    val tableNumber: Long,
    val status: TableStatus,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(table: Table, id: Long = 0): TableEntity = TableEntity(
            id = id,
            tableNumber = table.tableNumber,
            status = table.status,
            createdAt = table.createdAt
        )
    }
    fun toDomain() = Table(tableNumber, status, createdAt)
}
