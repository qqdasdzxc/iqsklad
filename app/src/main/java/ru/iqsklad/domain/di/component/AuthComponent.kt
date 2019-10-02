package ru.iqsklad.domain.di.component

import dagger.Subcomponent
import ru.iqsklad.domain.di.module.auth.AuthApiModule
import ru.iqsklad.domain.di.module.auth.AuthRepositoryModule
import ru.iqsklad.domain.di.module.auth.AuthViewModelModule
import ru.iqsklad.domain.di.scope.AuthScope
import ru.iqsklad.presentation.implementation.auth.ChooseForwarderViewModel
import ru.iqsklad.ui.auth.fragment.ChooseForwarderAuthFragment

@AuthScope
@Subcomponent(
    modules = [AuthViewModelModule::class, AuthRepositoryModule::class, AuthApiModule::class]
)
interface AuthComponent {

    fun inject(fragment: ChooseForwarderAuthFragment)

    fun inject(presenter: ChooseForwarderViewModel)
}