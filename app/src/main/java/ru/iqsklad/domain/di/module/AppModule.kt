package ru.iqsklad.domain.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(
    @get:Provides
    @get:Singleton
    val context: Context
)