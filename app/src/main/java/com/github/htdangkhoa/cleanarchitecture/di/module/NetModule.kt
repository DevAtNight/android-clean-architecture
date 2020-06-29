package com.github.htdangkhoa.cleanarchitecture.di.module

import android.content.Context
import com.blankj.utilcode.util.NetworkUtils
import com.github.htdangkhoa.cleanarchitecture.Constant
import com.github.htdangkhoa.cleanarchitecture.data.model.AuthModel
import com.github.htdangkhoa.cleanarchitecture.data.service.ApiService
import java.util.concurrent.TimeUnit
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetModule {
    val module = module {
        single { provideCache(get()) }

        single { provideOkHttpClient(get()) }

        single { provideRetrofit(get(), Constant.BASE_URL) }

        single { provideAppService(get()) }
    }

    private fun provideOkHttpClient(cache: Cache): OkHttpClient {
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .cache(cache)
            .addInterceptor { chain ->
                var request = chain.request()

                AuthModel.accessToken?.let {
                    request = request.newBuilder().header("Authorization", "Bearer $it").build()
                }

                val response = chain.proceed(request)

                response
            }
            .addNetworkInterceptor { chain ->
                var request = chain.request()

                request = if (NetworkUtils.isAvailable())
                    request
                        .newBuilder()
                        .header(
                            "Cache-Control",
                            "public, max-age=5"
                        )
                        .build()
                else
                    request
                        .newBuilder()
                        .header(
                            "Cache-Control",
                            "public, only-if-cached, max-stale=${60 * 60 * 24 * 7}"
                        )
                        .build()

                chain.proceed(request)
            }
            .addInterceptor(logger)
            .build()
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideCache(context: Context): Cache {
        val cacheSize: Long = 10 * 1024 * 1024 // 10mb

        return Cache(context.cacheDir, cacheSize)
    }

    private fun provideAppService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}
