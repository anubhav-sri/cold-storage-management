package com.saikrishna.wms.controllers

import com.saikrishna.wms.services.CustomerService
import com.saikrishna.wms.services.LotService
import com.saikrishna.wms.services.ReceiptService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.awt.image.BufferedImage

@RestController
@CrossOrigin
class QrCodeController(@Autowired private val lotService: LotService,
					   @Autowired private val customerService: CustomerService,
					   @Autowired private val receiptService: ReceiptService
) {

	@GetMapping("/receipt/{serialNumber}/qrcode", produces = ["application/octet-stream"])
	fun getReceiptByLotNumber(@PathVariable serialNumber: Int): ResponseEntity<ByteArray> {
		val lot = lotService.findByLotNumber(serialNumber).get()
		val customer = customerService.getCustomer(lot.customer)
		return ResponseEntity.ok().body(receiptService.generateQrCode(customer, lot));
	}
}
