package ru.iqsklad.domain.di.module.procedure

import dagger.Module
import dagger.Provides
import ru.iqsklad.data.scan.IRfidScanner
import ru.iqsklad.data.scan.RfidScanner
import ru.iqsklad.domain.di.scope.ProcedureScope

@Module
class ProcedureRfidScannerModule {

    @Provides
    @ProcedureScope
    fun getRfidScanner(): IRfidScanner = RfidScanner()
}