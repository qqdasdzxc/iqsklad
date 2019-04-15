package ru.iqsklad.domain.di.component

import dagger.Subcomponent
import ru.iqsklad.domain.di.module.procedure.ProcedureDataModule
import ru.iqsklad.domain.di.module.procedure.ProcedureRepositoryModule
import ru.iqsklad.domain.di.module.procedure.ProcedureViewModelModule
import ru.iqsklad.domain.di.scope.ProcedureScope
import ru.iqsklad.presentation.implementation.procedure.ChooseProcedureViewModel
import ru.iqsklad.presentation.implementation.procedure.ChooseStewardViewModel
import ru.iqsklad.ui.procedure.fragment.ChooseProcedureFragment
import ru.iqsklad.ui.procedure.fragment.ChooseStewardFragment

@ProcedureScope
@Subcomponent(
    modules = [ProcedureDataModule::class, ProcedureViewModelModule::class, ProcedureRepositoryModule::class]
)
interface ProcedureComponent {

    fun inject(fragment: ChooseProcedureFragment)

    fun inject(fragment: ChooseStewardFragment)

    fun inject(presenter: ChooseProcedureViewModel)

    fun inject(presenter: ChooseStewardViewModel)
}