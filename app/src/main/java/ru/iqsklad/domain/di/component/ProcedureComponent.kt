package ru.iqsklad.domain.di.component

import dagger.Subcomponent
import ru.iqsklad.domain.di.module.procedure.ProcedureDataModule
import ru.iqsklad.domain.di.module.procedure.ProcedureRepositoryModule
import ru.iqsklad.domain.di.module.procedure.ProcedureRfidScannerModule
import ru.iqsklad.domain.di.module.procedure.ProcedureViewModelModule
import ru.iqsklad.domain.di.scope.ProcedureScope
import ru.iqsklad.presentation.implementation.procedure.ChooseProcedureViewModel
import ru.iqsklad.presentation.implementation.procedure.ChooseStewardViewModel
import ru.iqsklad.presentation.implementation.procedure.InvoiceNumberInputViewModel
import ru.iqsklad.presentation.implementation.procedure.InventoryScanViewModel
import ru.iqsklad.ui.procedure.fragment.ChooseProcedureFragment
import ru.iqsklad.ui.procedure.fragment.ChooseStewardFragment
import ru.iqsklad.ui.procedure.fragment.InvoiceNumberInputFragment
import ru.iqsklad.ui.procedure.fragment.InventoryScanFragment

@ProcedureScope
@Subcomponent(
    modules = [ProcedureDataModule::class, ProcedureViewModelModule::class, ProcedureRepositoryModule::class,
    ProcedureRfidScannerModule::class]
)
interface ProcedureComponent {

    fun inject(fragment: ChooseProcedureFragment)

    fun inject(fragment: ChooseStewardFragment)

    fun inject(fragment: InvoiceNumberInputFragment)

    fun inject(fragment: InventoryScanFragment)

    fun inject(presenter: ChooseProcedureViewModel)

    fun inject(presenter: ChooseStewardViewModel)

    fun inject(presenter: InvoiceNumberInputViewModel)

    fun inject(presenter: InventoryScanViewModel)
}