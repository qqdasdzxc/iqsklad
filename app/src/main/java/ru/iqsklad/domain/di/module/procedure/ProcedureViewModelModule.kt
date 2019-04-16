package ru.iqsklad.domain.di.module.procedure

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.iqsklad.presentation.factory.ViewModelFactory
import ru.iqsklad.presentation.factory.ViewModelKey
import ru.iqsklad.presentation.implementation.procedure.ChooseProcedureViewModel
import ru.iqsklad.presentation.implementation.procedure.ChooseStewardViewModel
import ru.iqsklad.presentation.implementation.procedure.InvoiceNumberInputViewModel
import ru.iqsklad.presentation.implementation.procedure.InvoicePreviewViewModel

@Module
abstract class ProcedureViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(ChooseProcedureViewModel::class)
    abstract fun chooseProcedureViewModel(viewModel: ChooseProcedureViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChooseStewardViewModel::class)
    abstract fun chooseStewardViewModel(viewModel: ChooseStewardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InvoiceNumberInputViewModel::class)
    abstract fun invoiceNumberInputViewModel(viewModel: InvoiceNumberInputViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InvoicePreviewViewModel::class)
    abstract fun invoicePreviewViewModel(viewModel: InvoicePreviewViewModel): ViewModel
}