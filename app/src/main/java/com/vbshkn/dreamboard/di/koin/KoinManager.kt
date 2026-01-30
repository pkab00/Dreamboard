package com.vbshkn.dreamboard.di.koin

/*
    УСТАРЕВШАЯ СИСТЕМА ИНЪЕКЦИИ ЗАВИСИМОСТЕЙ С ПОМОЩЬЮ KOIN
    НЕАКТУАЛЬНА С МОМЕНТА ПЕРЕХОДА НА DAGGER/HILT
 */


//import android.content.Context
//import coil3.ImageLoader
//import coil3.disk.DiskCache
//import coil3.disk.directory
//import coil3.memory.MemoryCache
//import coil3.network.okhttp.OkHttpNetworkFetcherFactory
//import com.vbshkn.dreamboard.BuildConfig
//import com.vbshkn.dreamboard.data.local.datasource.UserSettingsDataStore
//import com.vbshkn.dreamboard.data.remote.api.VecteezyApi
//import com.vbshkn.dreamboard.data.remote.datasource.RemoteImageDataSource
//import com.vbshkn.dreamboard.data.remote.interceptor.AccountIdInterceptor
//import com.vbshkn.dreamboard.data.remote.interceptor.BearerAuthInterceptor
//import com.vbshkn.dreamboard.data.repository.ImageRepositoryImpl
//import com.vbshkn.dreamboard.data.repository.UserSettingsRepositoryImpl
//import com.vbshkn.dreamboard.data.service.ImageDownloadService
//import com.vbshkn.dreamboard.data.service.ShareDataService
//import com.vbshkn.dreamboard.domain.datasource.UserSettingsSource
//import com.vbshkn.dreamboard.domain.repository.ImageRepository
//import com.vbshkn.dreamboard.domain.repository.UserSettingsRepository
//import com.vbshkn.dreamboard.domain.service.ImageDownloader
//import com.vbshkn.dreamboard.domain.usecase.DownloadImageUseCase
//import com.vbshkn.dreamboard.domain.usecase.GetImageListUseCase
//import com.vbshkn.dreamboard.domain.usecase.GetImageUseCase
//import com.vbshkn.dreamboard.domain.usecase.GetUserSettingsUseCase
//import com.vbshkn.dreamboard.domain.usecase.ShareTextDataUseCase
//import com.vbshkn.dreamboard.domain.usecase.UpdateUserSettingsUseCase
//import com.vbshkn.dreamboard.presentation.MainViewModel
//import com.vbshkn.dreamboard.presentation.viewmodel.HomeViewModel
//import com.vbshkn.dreamboard.presentation.viewmodel.ImageDetailsViewModel
//import com.vbshkn.dreamboard.presentation.viewmodel.SearchViewModel
//import com.vbshkn.dreamboard.presentation.viewmodel.SettingsViewModel
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import org.koin.android.ext.koin.androidContext
//import org.koin.core.context.startKoin
//import org.koin.core.module.dsl.viewModelOf
//import org.koin.dsl.module
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//class KoinManager(private val context: Context) {
//    private val remoteModule = module {
//        single<OkHttpClient> {
//            OkHttpClient.Builder()
//                .addInterceptor(BearerAuthInterceptor(BuildConfig.VECTEEZY_SECRET_KEY))
//                .addInterceptor(AccountIdInterceptor())
//                .addInterceptor(
//                    HttpLoggingInterceptor()
//                        .apply { level = HttpLoggingInterceptor.Level.BODY }
//                )
//                .build()
//        }
//        single<Retrofit> {
//            Retrofit.Builder()
//                .baseUrl("https://api.vecteezy.com/")
//                .client(get())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//        }
//        single<VecteezyApi> { get<Retrofit>().create(VecteezyApi::class.java) }
//        single<RemoteImageDataSource> { RemoteImageDataSource(get()) }
//        single<ImageLoader> {
//            ImageLoader
//                .Builder(androidContext())
//                .components { add(OkHttpNetworkFetcherFactory()) }
//                .memoryCache { // 25% оперативки
//                    MemoryCache.Builder()
//                        .maxSizePercent(androidContext(), 0.25)
//                        .build()
//                }
//                .diskCache { // 2% на диске
//                    DiskCache.Builder()
//                        .directory(androidContext().cacheDir.resolve("image_cache"))
//                        .maxSizePercent(0.02)
//                        .build()
//                }
//                .build()
//        }
//    }
//
//    val localModule = module {
//        single<UserSettingsSource> { UserSettingsDataStore(androidContext()) }
//    }
//
//    val serviceModule = module {
//        single<ImageDownloader> { ImageDownloadService(androidContext()) }
//        single<ShareDataService> { ShareDataService(androidContext()) }
//    }
//
//    val repositoryModule = module {
//        single<ImageRepository> { ImageRepositoryImpl(get()) }
//        single<UserSettingsRepository> { UserSettingsRepositoryImpl(get()) }
//    }
//
//    val useCaseModule = module {
//        single { GetImageUseCase(get()) }
//        single { GetImageListUseCase(get()) }
//        single { DownloadImageUseCase(get()) }
//        single { ShareTextDataUseCase(get()) }
//        single { GetUserSettingsUseCase(get()) }
//        single { UpdateUserSettingsUseCase(get()) }
//    }
//
//    val viewModelModule = module {
//        viewModelOf(::MainViewModel)
//        viewModelOf(::HomeViewModel)
//        viewModelOf(::SearchViewModel)
//        viewModelOf(::SettingsViewModel)
//        viewModelOf(::ImageDetailsViewModel)
//    }
//
//    fun startFull() = startKoin {
//        androidContext(context)
//        modules(
//            listOf(
//                remoteModule, localModule,
//                serviceModule, repositoryModule, useCaseModule,
//                viewModelModule
//            )
//        )
//    }
//}