package ru.iqsklad.domain.di.module.procedure

import dagger.Module
import dagger.Provides
import ru.iqsklad.data.dto.procedure.ProcedureDataHolder
import ru.iqsklad.domain.di.scope.ProcedureScope

@Module
class ProcedureDataModule {

    @ProcedureScope
    @Provides
    fun getProcedureDataHolder(): ProcedureDataHolder = ProcedureDataHolder()
}