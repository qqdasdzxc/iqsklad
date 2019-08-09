package ru.iqsklad.domain.di.component

import dagger.Subcomponent
import ru.iqsklad.domain.di.module.procedure.ProcedureDataModule
import ru.iqsklad.domain.di.module.procedure.ProcedureRepositoryModule
import ru.iqsklad.domain.di.module.procedure.ScannerFactoryModule
import ru.iqsklad.domain.di.module.procedure.ProcedureViewModelModule
import ru.iqsklad.domain.di.scope.ProcedureScope
import ru.iqsklad.presentation.implementation.procedure.*
import ru.iqsklad.ui.procedure.fragment.*

@ProcedureScope
@Subcomponent(
    modules = [ProcedureDataModule::class, ProcedureViewModelModule::class, ProcedureRepositoryModule::class,
    ScannerFactoryModule::class]
)
interface ProcedureComponent {

    fun inject(fragment: ChooseProcedureFragment)

    fun inject(fragment: ChooseStewardFragment)

    fun inject(fragment: InvoiceNumberInputFragment)

    fun inject(fragment: InventoryScanFragment)

    fun inject(fragment: ProcedureSuccessFragment)

    fun inject(presenter: ChooseProcedureViewModel)

    fun inject(presenter: ChooseStewardViewModel)

    fun inject(presenter: InvoiceNumberInputViewModel)

    fun inject(presenter: InventoryScanViewModel)

    fun inject(presenter: ProcedureSuccessViewModel)
}