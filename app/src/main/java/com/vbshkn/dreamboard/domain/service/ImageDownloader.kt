package com.vbshkn.dreamboard.domain.service

import com.vbshkn.dreamboard.domain.model.ImageEntity

interface ImageDownloader {
    fun downloadImage(imageEntity: ImageEntity): Long
}