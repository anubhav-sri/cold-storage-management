package com.saikrishna.wms.controllers

import com.saikrishna.wms.models.Customer
import com.saikrishna.wms.services.CustomerService
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

internal class CustomerControllerTest {
    private val customerService: CustomerService = mockk()

    @Test
    fun shouldBeAbleToGetCustomer(){
        val customerId = UUID.randomUUID()

        val expectedCustomer = Customer(customerId,"","","","99999");
        every { customerService.getCustomer(customerId) } returns expectedCustomer

        val customer: Customer? = CustomerController(customerService)
                .getCustomer(customerId).body

        assertThat(customer).isEqualTo(expectedCustomer)

    }
}
