package ru.iqsklad.domain.di.module

import dagger.Module
import dagger.Provides
import ru.iqsklad.data.scan.ScannerFactory
import javax.inject.Singleton

@Module
class ScannerFactoryModule {

    @Singleton
    @Provides
    fun getScannerFactory(): ScannerFactory = ScannerFactory()
}