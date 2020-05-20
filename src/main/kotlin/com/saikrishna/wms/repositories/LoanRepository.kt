package com.saikrishna.wms.repositories

import com.saikrishna.wms.models.Loan
import org.springframework.data.repository.CrudRepository
import java.util.*

interface LoanRepository : CrudRepository<Loan, UUID>