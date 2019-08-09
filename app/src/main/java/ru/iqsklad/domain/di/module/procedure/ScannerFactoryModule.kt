package ru.iqsklad.domain.di.module.procedure

import dagger.Module
import dagger.Provides
import ru.iqsklad.data.scan.ScannerFactory
import ru.iqsklad.domain.di.scope.ProcedureScope

@Module
class ScannerFactoryModule {

    @Provides
    @ProcedureScope
    fun getScannerFactory(): ScannerFactory = ScannerFactory()
}