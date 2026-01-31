package com.vbshkn.dreamboard.di.hilt

import com.vbshkn.dreamboard.data.local.datasource.UserSettingsDataStore
import com.vbshkn.dreamboard.data.remote.datasource.RemoteImageDataSource
import com.vbshkn.dreamboard.domain.datasource.ImageDataSource
import com.vbshkn.dreamboard.domain.datasource.UserSettingsSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module @InstallIn(SingletonComponent::class)
interface HiltDataSourceModule {

    @Binds @Singleton
    fun bindUserSettingsSource(impl: UserSettingsDataStore): UserSettingsSource

    @Binds @Singleton
    fun bindImageSource(impl: RemoteImageDataSource): ImageDataSource
}