package com.vbshkn.dreamboard.util

enum class ImageFormat(
    val apiName: String,
    val formatSuffix: String,
    val mimeType: String
) {
    PHOTO("photo", "jpg","image/jpeg"),
    PNG("png", "png", "image/png"),
    PSD("psd", "psd", "application/photoshop"),
    SVG("vector", "svg", "image/svg+xml");

    companion object {
        fun fromString(name: String?) = entries.find {
            it.apiName == name
        } ?: PHOTO
    }
}