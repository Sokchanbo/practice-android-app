package com.example.practiceapp.di

import com.example.practiceapp.BuildConfig
import com.example.practiceapp.network.api.GithubAPI
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.orhanobut.logger.Logger
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideAuthApi(retrofit: Retrofit): GithubAPI = retrofit.create(GithubAPI::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addConverterFactory(gsonConverterFactory)
        .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            /*.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)*/
            .addInterceptor(interceptor)
            .build()

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Logger.d(message)
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()
}
