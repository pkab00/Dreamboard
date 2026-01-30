package com.vbshkn.dreamboard.di.hilt

import com.vbshkn.dreamboard.data.service.ImageDownloadService
import com.vbshkn.dreamboard.domain.service.ImageDownloader
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module @InstallIn(SingletonComponent::class)
interface HiltServiceModule {

    @Binds @Singleton
    fun bindImageDownloader(impl: ImageDownloadService): ImageDownloader
}