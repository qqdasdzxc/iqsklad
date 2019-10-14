package ru.iqsklad.domain.di.module

import dagger.Module
import dagger.Provides
import ru.iqsklad.data.dto.procedure.ProcedureDataHolder
import javax.inject.Singleton

@Module
class ProcedureDataModule {

    @Singleton
    @Provides
    fun getProcedureDataHolder(): ProcedureDataHolder = ProcedureDataHolder()
}