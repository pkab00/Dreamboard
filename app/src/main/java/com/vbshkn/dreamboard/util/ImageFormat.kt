package com.vbshkn.dreamboard.util

sealed class ImageFormat(val value: String) {
    object Any : ImageFormat("photo")
    object Png : ImageFormat("png")
    object Psd : ImageFormat("psd")
    object Svg : ImageFormat("svg")
}