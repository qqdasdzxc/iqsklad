package ru.iqsklad.domain.di.module.procedure

import dagger.Module
import dagger.Provides
import ru.iqsklad.data.repository.contract.IStewardsRepository
import ru.iqsklad.data.repository.implement.StewardsRepository
import ru.iqsklad.domain.di.scope.ProcedureScope

@Module
class ProcedureRepositoryModule {

    @ProcedureScope
    @Provides
    fun getStewardsRepository(): IStewardsRepository = StewardsRepository()
}