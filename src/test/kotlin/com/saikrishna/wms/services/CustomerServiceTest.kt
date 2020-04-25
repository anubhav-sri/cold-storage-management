package com.saikrishna.wms.services

import com.saikrishna.wms.models.Customer
import com.saikrishna.wms.repositories.CustomerRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

internal class CustomerServiceTest {
    private val customerRepo: CustomerRepository = mockk()

    private var customerService: CustomerService = CustomerService(customerRepo)

    @Test
    fun shouldSaveTheCustomer() {
        val customer = Customer(UUID.randomUUID(), "name", "fname", "addr", "1212")
        every { customerRepo.save(customer) } returns customer
        customerService.saveCustomer(customer)
        verify { customerRepo.save(customer) }
    }

    @Test
    fun shouldGetTheCustomer() {
        val id = UUID.randomUUID()
        val customer = Customer(id, "name", "fname", "addr", "1212")
        every { customerRepo.findById(id) } returns Optional.of(customer)
        val actualCustomer = customerService.getCustomer(id)
        verify { customerRepo.findById(id) }
        assertThat(actualCustomer).isEqualTo(customer)
    }

    @Test
    fun `should save all customers`() {
        val customer = Customer(UUID.randomUUID(), "name", "fname", "addr", "1212")
        every { customerRepo.saveAll(listOf(customer)) } returns listOf(customer)
        customerService.saveAll(listOf(customer))
        verify { customerRepo.saveAll(listOf(customer)) }
    }

}
