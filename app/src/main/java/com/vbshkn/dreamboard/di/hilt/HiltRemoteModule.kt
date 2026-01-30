package com.vbshkn.dreamboard.di.hilt

import android.content.Context
import coil3.ImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import com.vbshkn.dreamboard.BuildConfig
import com.vbshkn.dreamboard.data.remote.api.VecteezyApi
import com.vbshkn.dreamboard.data.remote.interceptor.AccountIdInterceptor
import com.vbshkn.dreamboard.data.remote.interceptor.BearerAuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module @InstallIn(SingletonComponent::class)
object HiltRemoteModule {

    @Provides @Singleton @Named("api_key")
    fun provideApiSecretKey(): String {
        return BuildConfig.VECTEEZY_SECRET_KEY
    }

    @Provides @Singleton
    fun provideOkHttpClient(
        @Named("api_key") apiKey: String
        ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(BearerAuthInterceptor(apiKey))
            .addInterceptor(AccountIdInterceptor())
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply { level = HttpLoggingInterceptor.Level.BODY }
            ).build()
    }

    @Provides @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.vecteezy.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides @Singleton
    fun provideApi(retrofit: Retrofit): VecteezyApi {
        return retrofit.create(VecteezyApi::class.java)
    }

    @Provides @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context
    ): ImageLoader {
        return ImageLoader
            .Builder(context)
            .components { add(OkHttpNetworkFetcherFactory()) }
            .memoryCache { // 25% оперативки
                MemoryCache.Builder()
                    .maxSizePercent(context, 0.25)
                    .build()
            }
            .diskCache { // 2% на диске
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }.build()
    }
}