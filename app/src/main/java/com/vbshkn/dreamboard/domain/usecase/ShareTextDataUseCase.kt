package com.vbshkn.dreamboard.domain.usecase

import com.vbshkn.dreamboard.data.service.ShareDataService

class ShareTextDataUseCase(
    private val shareDataService: ShareDataService
) {
    operator fun invoke(text: String, title: String) {
        shareDataService.shareString(text, title)
    }
}