package com.alaa.alaagallo.di

import com.alaa.Constants
import com.alaa.data.service.AccountsApiService
import com.alaa.alaagallo.util.AccountsInterceptor
import com.alaa.data.service.InvoiceApiService
import com.alaa.data.service.ProductApiService
import com.alaa.data.service.UserApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {

    @Provides
    @Singleton
    fun provideHttpInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttp(
        interceptor: AccountsInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).addInterceptor(
            httpLoggingInterceptor
        )
            .readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).baseUrl(
                Constants.BASE_URL
            ).build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): AccountsApiService {
        return retrofit.create(AccountsApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideProductApiService(retrofit: Retrofit): ProductApiService {
        return retrofit.create(ProductApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideInvoiceApiService(retrofit: Retrofit): InvoiceApiService {
        return retrofit.create(InvoiceApiService::class.java)
    }
}