package com.vbshkn.dreamboard.di.hilt

import com.vbshkn.dreamboard.data.repository.ImageRepositoryImpl
import com.vbshkn.dreamboard.data.repository.UserSettingsRepositoryImpl
import com.vbshkn.dreamboard.domain.repository.ImageRepository
import com.vbshkn.dreamboard.domain.repository.UserSettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module @InstallIn(SingletonComponent::class)
interface HiltRepositoryModule {

    @Binds @Singleton
    fun bindImageRepository(impl: ImageRepositoryImpl): ImageRepository

    @Binds @Singleton
    fun bindUserSettingsRepository(impl: UserSettingsRepositoryImpl): UserSettingsRepository
}