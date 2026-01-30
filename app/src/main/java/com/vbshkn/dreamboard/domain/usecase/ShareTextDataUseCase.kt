package com.vbshkn.dreamboard.domain.usecase

import com.vbshkn.dreamboard.data.service.ShareDataService
import javax.inject.Inject

class ShareTextDataUseCase @Inject constructor(
    private val shareDataService: ShareDataService
) {
    operator fun invoke(text: String, title: String) {
        shareDataService.shareString(text, title)
    }
}