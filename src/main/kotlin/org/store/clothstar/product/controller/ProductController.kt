package org.store.clothstar.product.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.store.clothstar.category.service.CategoryService
import org.store.clothstar.common.dto.MessageDTO
import org.store.clothstar.common.util.URIBuilder.buildURI
import org.store.clothstar.product.dto.request.UpdateProductRequest
import org.store.clothstar.product.dto.response.ProductResponse
import org.store.clothstar.product.service.ProductService
import java.net.URI

@Tag(name = "Products", description = "Products(상품 옵션) 관련 API 입니다.")
@RestController
class ProductController(
    private val productService: ProductService,
    private val categoryService: CategoryService,
) {
    @Operation(summary = "상품 옵션 상세 조회", description = "productId로 상품 옵션 한개를 상세 조회한다.")
    @GetMapping("/{productId}")
    fun getProduct(@PathVariable("productId") productId: Long): ResponseEntity<ProductResponse> {
        val productResponse: ProductResponse = productService.getProduct(productId)
        return ResponseEntity.ok().body<ProductResponse>(productResponse)
    }

    @Operation(summary = "상품 옵션 등록", description = "상품 옵션 이름, 추가금액, 재고 수를 입력하여 상품을 신규 등록한다.")
    @PostMapping
    fun createProduct(@Validated @RequestBody createProductRequest: CreateProductRequest): ResponseEntity<URI> {
        val productId: Long = productService.createProduct(createProductRequest)
        val location = buildURI(productId)

        return ResponseEntity.created(location).build()
    }

    @Operation(summary = "상품 옵션 수정", description = "상품 옵션 이름, 추가금액, 재고 수를 입력하여 상품 옵션 정보를 수정한다.")
    @PutMapping("/{productId}")
    fun updateProduct(
        @PathVariable("productId") productId: Long,
        @Validated @RequestBody updateProductRequest: UpdateProductRequest
    ): ResponseEntity<MessageDTO> {
        productService.updateProduct(productId, updateProductRequest)

        return ResponseEntity.ok().body(MessageDTO(HttpStatus.OK.value(), "Product updated successfully"))
    }

    @Operation(summary = "상품 옵션 삭제", description = "상품 옵션 id로 상품 옵션을 삭제한다.")
    @DeleteMapping("/{productId}")
    fun deleteProduct(@PathVariable productId: Long): ResponseEntity<Void> {
        productService.deleteProduct(productId)

        return ResponseEntity.noContent().build()
    }
}