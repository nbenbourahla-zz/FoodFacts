package com.benbourahla.foodfacts.api

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val okhttpBuilder = OkHttpClient.Builder()
        okhttpBuilder.networkInterceptors()
                .add(StethoInterceptor())
        return okhttpBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .baseUrl("http://world.openfoodfacts.org/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

    @Provides
    @Singleton
    fun provideProductService(retrofit: Retrofit): ProductService = retrofit.create(ProductService::class.java)
}