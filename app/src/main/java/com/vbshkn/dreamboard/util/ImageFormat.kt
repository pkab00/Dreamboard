package com.vbshkn.dreamboard.util

sealed class ImageFormat(val value: String) {
    object Photo : ImageFormat("photo")
    object Png : ImageFormat("png")
    object Psd : ImageFormat("psd")
    object Svg : ImageFormat("svg")
}