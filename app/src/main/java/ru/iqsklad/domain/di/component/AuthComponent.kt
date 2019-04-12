package ru.iqsklad.domain.di.component

import dagger.Subcomponent
import ru.iqsklad.domain.di.module.auth.AuthRepositoryModule
import ru.iqsklad.domain.di.module.auth.AuthViewModelModule
import ru.iqsklad.domain.di.scope.AuthScope
import ru.iqsklad.presentation.implementation.auth.ChooseUserViewModel
import ru.iqsklad.ui.auth.fragment.ChooseUserAuthFragment

@AuthScope
@Subcomponent(
    modules = [AuthViewModelModule::class, AuthRepositoryModule::class]
)
interface AuthComponent {

    fun inject(fragment: ChooseUserAuthFragment)

    fun inject(presenter: ChooseUserViewModel)
}