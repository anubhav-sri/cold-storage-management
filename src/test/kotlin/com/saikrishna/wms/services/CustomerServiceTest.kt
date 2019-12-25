package com.saikrishna.wms.services

import com.saikrishna.wms.models.Customer
import com.saikrishna.wms.models.Name
import com.saikrishna.wms.repositories.CustomerRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify

internal class CustomerServiceTest {
    private val customerRepo: CustomerRepository = Mockito.mock(CustomerRepository::class.java)

    private var customerService: CustomerService = CustomerService(customerRepo)

    @Test
    fun shouldSaveTheCustomer() {
        val name = Name("fname", "mname", "lname")
        val customer = Customer(name, name, "addr", 1212)
        customerService.saveCustomer(customer)
        verify(customerRepo).save(customer)
    }

}
