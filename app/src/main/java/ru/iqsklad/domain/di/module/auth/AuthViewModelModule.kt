package ru.iqsklad.domain.di.module.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.iqsklad.domain.di.component.AuthComponent
import ru.iqsklad.domain.di.scope.AuthScope
import ru.iqsklad.presentation.factory.ViewModelFactory
import ru.iqsklad.presentation.factory.ViewModelKey
import ru.iqsklad.presentation.implementation.auth.ChooseUserViewModel

@Module
abstract class AuthViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(ChooseUserViewModel::class)
    abstract fun chooseUserViewModel(viewModel: ChooseUserViewModel): ViewModel
}