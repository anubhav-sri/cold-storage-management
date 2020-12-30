package com.saikrishna.wms.services

import com.saikrishna.wms.models.*
import org.springframework.stereotype.Service
import java.awt.image.BufferedImage
import java.io.File

@Service
class ReceiptService(private val qrCodeGenerator: QrCodeGenerator) {

	fun generateQrCode(customer: Customer, lot: Lot) : ByteArray {
		return qrCodeGenerator.generateQrCode(customer,lot)
	}
}
