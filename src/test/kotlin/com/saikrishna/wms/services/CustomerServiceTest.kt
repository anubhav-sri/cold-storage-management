package com.saikrishna.wms.services

import com.saikrishna.wms.models.Customer
import com.saikrishna.wms.repositories.CustomerRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import java.util.*

internal class CustomerServiceTest {
    private val customerRepo: CustomerRepository = Mockito.mock(CustomerRepository::class.java)

    private var customerService: CustomerService = CustomerService(customerRepo)

    @Test
    fun shouldSaveTheCustomer() {
        val customer = Customer(UUID.randomUUID(), "name", "fname", "addr", 1212)
        customerService.saveCustomer(customer)
        verify(customerRepo).save(customer)
    }

}
