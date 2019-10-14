package ru.iqsklad.domain.di.module

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.dtk.lib.network.builder.DtkNetBuilder
import ru.iqsklad.data.Constants
import ru.iqsklad.utils.ConverterUtils
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ClientModule {

    @Provides
    @FlowPreview
    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    fun getController(): DtkNetBuilder = DtkNetBuilder()

    @Singleton
    @Provides
    fun getService(context: Context): Retrofit {
        return Retrofit.Builder()
                .baseUrl(Constants.SERVER_URL_API_DEBUG)
                .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
                .addConverterFactory(GsonConverterFactory.create(ConverterUtils.getGsonConverter()))
                .client(buildOkHttpClient(context))
                .build()
    }

    private fun buildOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(Constants.TIMEOUT_REQUEST, TimeUnit.SECONDS)
                .readTimeout(Constants.TIMEOUT_REQUEST, TimeUnit.SECONDS)
                .writeTimeout(Constants.TIMEOUT_REQUEST, TimeUnit.SECONDS)
                .addInterceptor(ChuckInterceptor(context))
                .build()
    }
}