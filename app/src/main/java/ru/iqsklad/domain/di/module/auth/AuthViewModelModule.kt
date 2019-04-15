package ru.iqsklad.domain.di.module.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.iqsklad.presentation.factory.ViewModelFactory
import ru.iqsklad.presentation.factory.ViewModelKey
import ru.iqsklad.presentation.implementation.auth.ChooseForwarderViewModel

@Module
abstract class AuthViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(ChooseForwarderViewModel::class)
    abstract fun chooseForwarderViewModel(viewModel: ChooseForwarderViewModel): ViewModel
}