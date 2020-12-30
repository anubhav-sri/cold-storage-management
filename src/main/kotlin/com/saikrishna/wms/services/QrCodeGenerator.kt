package com.saikrishna.wms.services


import com.google.zxing.EncodeHintType
import com.google.zxing.pdf417.encoder.BarcodeMatrix
import com.saikrishna.wms.models.Customer
import com.saikrishna.wms.models.Lot
import com.saikrishna.wms.models.QrCodeData
import net.glxn.qrgen.javase.QRCode
import org.springframework.stereotype.Component
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*


@Component
class QrCodeGenerator {
	fun generateQrCode(customer: Customer, lot: Lot): ByteArray {
		QrCodeData(lot.serialNumber,lot.numberOfBags).toString()
		val stream: ByteArrayOutputStream = QRCode
				.from(QrCodeData(lot.serialNumber,lot.numberOfBags).toString())
				.withSize(80, 80)
				.withHint(EncodeHintType.MARGIN, 2)
				.stream()
		val bis = ByteArrayInputStream(stream.toByteArray())

		return Base64.getEncoder().encode(bis.readAllBytes())
	}


}
