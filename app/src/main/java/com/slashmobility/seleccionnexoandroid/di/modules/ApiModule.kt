package com.slashmobility.seleccionnexoandroid.di.modules

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.slashmobility.seleccionnexoandroid.BuildConfig
import com.slashmobility.seleccionnexoandroid.remote.api.ApiClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Leo ¯\_(ツ)_/¯ on 2019-12-02
 */

@Module
class ApiModule {

    companion object {

        const val API_BASE_URL = BuildConfig.API_HOST
        const val API_TIMEOUT = BuildConfig.API_TIMEOUT
    }

    internal val requestHeader: OkHttpClient
        @Provides
        @Named("BasicHeader")
        @Singleton
        get() {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()

            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .build()
                chain.proceed(request)
            }.addInterceptor(interceptor)
                .connectTimeout(API_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(API_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(API_TIMEOUT, TimeUnit.SECONDS)

            return httpClient.build()
        }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val builder =
            GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return builder.setLenient().create()
    }

    @Provides
    @Named("BaseRetrofit")
    @Singleton
    internal fun provideRetrofit(gson: Gson, @Named("BasicHeader") okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    internal fun provideApi(@Named("BaseRetrofit") retrofit: Retrofit): ApiClient {
        return retrofit.create(ApiClient::class.java)
    }
}