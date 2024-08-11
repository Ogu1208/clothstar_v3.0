package org.store.clothstar.product.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.store.clothstar.common.dto.MessageDTO
import org.store.clothstar.product.dto.request.ProductCreateRequest
import org.store.clothstar.product.dto.request.UpdateDisplayStatusRequest
import org.store.clothstar.product.service.ProductApplicationService
import org.store.clothstar.product.service.ProductService

@Tag(name = "Products", description = "Products(상품 옵션) 관련 API 입니다.")
@RequestMapping("/v3/products")
@RestController
private class ProductController(
    private val productApplicationService: ProductApplicationService,
    ) {
    @PostMapping
    @Operation(
        summary = "상품 등록",
        description = "카테고리 아이디, 상품 이름, 내용, 가격, 상태를 입력하여 상품을 신규 등록한다."
    )
    fun createProduct(
        @RequestPart(value = "mainImage", required = false) mainImage: MultipartFile,
        @RequestPart(value = "subImages", required = false) subImages: List<MultipartFile>?,
        @RequestPart(value = "dto") @Validated productCreateRequest: ProductCreateRequest
    ): ResponseEntity<MessageDTO> {
        // 상품 등록
        productApplicationService.createProduct(mainImage, subImages, productCreateRequest);

        val messageDTO = MessageDTO(
            HttpStatus.CREATED.value(),
            "상품 생성이 정상적으로 처리됐습니다."
        )

        return ResponseEntity(messageDTO, HttpStatus.CREATED)

    }

    @PatchMapping("/{productId}/displayStatus")
    @Operation(summary = "상품 진열 상태 변경", description = "상품 ID를 사용하여 해당 상품의 진열 상태를 변경합니다.")
    fun updateProductDisplayStatus(
        @PathVariable productId: Long,
        @RequestBody request: UpdateDisplayStatusRequest
    ): ResponseEntity<MessageDTO> {
        productApplicationService.updateProductDisplayStatus(productId, request.displayStatus)

        val messageDTO = MessageDTO(
            HttpStatus.OK.value(),
            "상품 진열 상태가 성공적으로 변경되었습니다."
        )
        return ResponseEntity(messageDTO, HttpStatus.OK)
    }

    @PatchMapping("/{productId}/items/{itemId}/displayStatus")
    @Operation(summary = "아이템 진열 상태 변경", description = "상품 ID와 아이템 ID를 사용하여 해당 아이템의 진열 상태를 변경합니다.")
    fun updateItemDisplayStatus(
        @PathVariable productId: Long,
        @PathVariable itemId: Long,
        @RequestBody request: UpdateDisplayStatusRequest
    ): ResponseEntity<MessageDTO> {
        productApplicationService.updateItemDisplayStatus(productId, itemId, request.displayStatus)

        val messageDTO = MessageDTO(
            HttpStatus.OK.value(),
            "아이템 진열 상태가 성공적으로 변경되었습니다."
        )
        return ResponseEntity(messageDTO, HttpStatus.OK)
    }
}