package com.saikrishna.wms.repositories

import com.saikrishna.wms.models.Customer
import org.springframework.data.repository.CrudRepository
import java.util.*

interface CustomerRepository : CrudRepository<Customer, UUID>
