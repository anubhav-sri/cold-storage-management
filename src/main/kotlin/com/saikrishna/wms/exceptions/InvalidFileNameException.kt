package com.saikrishna.wms.exceptions

class InvalidFileNameException(name: String) : RuntimeException("File should be a csv file. File Name:$name") {
}