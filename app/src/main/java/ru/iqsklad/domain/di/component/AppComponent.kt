package ru.iqsklad.domain.di.component

import dagger.Component
import ru.iqsklad.domain.App
import ru.iqsklad.domain.di.module.*
import ru.iqsklad.presentation.implementation.procedure.ChooseProcedureViewModel
import ru.iqsklad.presentation.implementation.procedure.FindInvoiceViewModel
import ru.iqsklad.presentation.implementation.procedure.InventoryScanViewModel
import ru.iqsklad.presentation.implementation.procedure.ProcedureSuccessViewModel
import ru.iqsklad.presentation.implementation.update.UpdateViewModel
import ru.iqsklad.presentation.implementation.user.ChooseUserViewModel
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class, ClientModule::class, RequestModule::class, ApiModule::class, RepositoryModule::class,
    DaoModule::class, ProcedureDataModule::class, ScannerFactoryModule::class, ProcedureResultsSenderModule::class]
)
interface AppComponent {

    fun inject(presenter: ChooseUserViewModel)

    fun inject(presenter: ChooseProcedureViewModel)

    fun inject(presenter: InventoryScanViewModel)

    fun inject(presenter: ProcedureSuccessViewModel)

    fun inject(presenter: UpdateViewModel)

    fun inject(presenter: FindInvoiceViewModel)

    fun inject(app: App)
}