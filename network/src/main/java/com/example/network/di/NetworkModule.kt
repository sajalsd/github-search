package com.example.network.di

import com.example.network.BuildConfig
import com.example.network.data.api.GithubApiService
import com.example.network.di.qualifiers.HttpInterceptorSet
import com.example.network.provider.OkHttpProvider
import com.example.network.provider.RetrofitProvider
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .build()

    @Provides
    @Singleton
    @HttpInterceptorSet
    fun provideHttpInterceptors(): Set<Interceptor> {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
        }

        return setOf(loggingInterceptor)
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        @HttpInterceptorSet interceptorSet: Set<@JvmSuppressWildcards Interceptor>
    ): OkHttpClient {
        return OkHttpProvider.getOkHttpClient(interceptors = interceptorSet)
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return RetrofitProvider.getRetrofitClient(okHttpClient = okHttpClient, moshi = moshi)
    }

    @Provides
    @Singleton
    fun provideGitApiService(
        retrofit: Retrofit
    ): GithubApiService {
        return retrofit.create(GithubApiService::class.java)
    }
}
