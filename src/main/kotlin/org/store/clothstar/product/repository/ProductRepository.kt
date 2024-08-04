package org.store.clothstar.product.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.store.clothstar.product.domain.entity.Product

@Repository
interface ProductRepository : JpaRepository<Product, Long> {

}