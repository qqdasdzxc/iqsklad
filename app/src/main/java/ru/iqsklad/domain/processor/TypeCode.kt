package ru.iqsklad.domain.processor

/**

 **/

enum class TypeCode(val code: Int) {
    ERROR_NETWORK_CONNECTED(-1),
    INVOICE_NOT_FOUND(0)
}