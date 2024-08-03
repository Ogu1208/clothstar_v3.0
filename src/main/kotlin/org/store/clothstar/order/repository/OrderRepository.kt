package org.store.clothstar.order.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.store.clothstar.order.domain.Order
import org.store.clothstar.order.domain.vo.Status

interface OrderRepository : JpaRepository<Order, Long> {
    fun findByOrderId(orderId: Long): Order?
}