package com.vbshkn.dreamboard

import android.content.Context
import com.vbshkn.dreamboard.data.remote.AccountIdInterceptor
import com.vbshkn.dreamboard.data.remote.BearerAuthInterceptor
import com.vbshkn.dreamboard.data.remote.RemoteImageDataSource
import com.vbshkn.dreamboard.data.remote.VecteezyApi
import com.vbshkn.dreamboard.data.repository.ImageRepository
import com.vbshkn.dreamboard.presentation.viewmodel.TestViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class KoinManager(private val context: Context) {
    private val remoteModule = module {
        single { BuildConfig.VECTEEZY_SECRET_KEY }
        single {
            OkHttpClient.Builder()
                .addInterceptor(BearerAuthInterceptor(get()))
                .addInterceptor(AccountIdInterceptor())
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .apply { level = HttpLoggingInterceptor.Level.BODY }
                )
                .build()
        }
        single {
            Retrofit.Builder()
                .baseUrl("https://api.vecteezy.com/")
                .client(get())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        single { get<Retrofit>().create(VecteezyApi::class.java) }
        single { RemoteImageDataSource(get()) }
    }

    val repositoryModule = module {
        single { ImageRepository(get()) }
    }

    val viewModelModule = module {
        viewModelOf(::TestViewModel)
    }

    fun startFull() = startKoin {
        androidContext(context)
        modules(
            listOf(
                remoteModule,
                repositoryModule,
                viewModelModule
            )
        )
    }
}