package org.store.clothstar.order.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import org.store.clothstar.common.error.ErrorCode
import org.store.clothstar.order.domain.Order
import org.store.clothstar.order.domain.vo.Status
import org.store.clothstar.common.error.exception.order.OrderNotFoundException
import org.store.clothstar.member.repository.MemberRepository
import org.store.clothstar.member.service.AddressService
import org.store.clothstar.member.service.MemberService
import org.store.clothstar.member.service.SellerService
import org.store.clothstar.order.repository.OrderRepository
import org.store.clothstar.product.service.ItemService
import org.store.clothstar.product.service.ProductService
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class OrderSellerServiceTest {
    @InjectMockKs
    lateinit var orderSellerService: OrderSellerService

    @MockK
    lateinit var orderRepository: OrderRepository

    @MockK
    lateinit var memberService: MemberService

    @MockK
    lateinit var sellerService: SellerService

    @MockK
    lateinit var addressService: AddressService

    @MockK
    lateinit var itemService: ItemService

    @MockK
    lateinit var productService: ProductService

    @MockK
    lateinit var order: Order

    // 판매자 주문 승인 - approveOrder
    @Test
    @DisplayName("판매자 주문 승인 - 성공 테스트")
    fun approveOrder_success_test() {
        //given
        val orderId = 1L

        every { order.status } returns Status.CONFIRMED
        every { orderRepository.findByIdOrNull(orderId) } returns order
        justRun { order.validateForStatusCONFIRMEDAndDeletedAt() }
        every { order.updateStatus(Status.PROCESSING) } answers {
            every { order.status } returns Status.PROCESSING
        }

        //when
        orderSellerService.approveOrder(orderId)

        //then
        verify(exactly = 1) { orderRepository.findByIdOrNull(orderId) }
        verify(exactly = 1) { order.validateForStatusCONFIRMEDAndDeletedAt() }
        verify(exactly = 1) { order.updateStatus(Status.PROCESSING) }
        assertEquals( Status.PROCESSING, order.status )
    }

    @Test
    @DisplayName("판매자 주문 승인 - 주문번호가 존재하지 않을 때 예외처리 테스트")
    fun approveOrder_orderNotFound_exception_test() {
        //given
        val orderId = 1L
        every { order.status } returns Status.CONFIRMED
        every { orderRepository.findByIdOrNull(orderId) } returns null

        //when & then
        val exception = assertThrows<OrderNotFoundException> {
            orderSellerService.approveOrder(orderId)
        }
        assertEquals(ErrorCode.NOT_FOUND_ORDER, exception.errorCode)
    }

    // 판매자 주문 취소 - cancelOrder
    @Test
    @DisplayName("판매자 주문 취소 - 성공 테스트")
    fun cancelOrder_success_test() {
        //given
        val orderId = 1L
        every { order.status } returns Status.CONFIRMED
        every { orderRepository.findByIdOrNull(orderId) } returns order
        justRun { order.validateForStatusCONFIRMEDAndDeletedAt() }
        every { order.updateStatus(Status.CANCELED) } answers {
            every { order.status } returns Status.CANCELED
        }

        //when
        orderSellerService.cancelOrder(orderId)

        //then
        verify(exactly = 1) { orderRepository.findByIdOrNull(orderId) }
        verify(exactly = 1) { order.validateForStatusCONFIRMEDAndDeletedAt() }
        verify(exactly = 1) { order.updateStatus(Status.CANCELED) }
        assertEquals( Status.CANCELED, order.status )
    }

    @Test
    @DisplayName("판매자 주문 취소 - 주문번호가 존재하지 않을 때 예외처리 테스트")
    fun cancelOrder_orderNotFound_exception_test() {
        //given
        val orderId = 1L
        every { order.status } returns Status.CONFIRMED
        every { orderRepository.findByIdOrNull(orderId) } returns null

        //when & then
        val exception = assertThrows<OrderNotFoundException> {
            orderSellerService.cancelOrder(orderId)
        }
        assertEquals(ErrorCode.NOT_FOUND_ORDER, exception.errorCode)
    }
}