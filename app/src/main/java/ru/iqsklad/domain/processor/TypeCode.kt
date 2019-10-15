package ru.iqsklad.domain.processor

/**

 **/

enum class TypeCode(val code: Int) {
    ERROR_NETWORK_CONNECTED(-1),
    SSL_PEER_UNVERIFIED(0),
    INVOICE_NOT_FOUND(1)
}