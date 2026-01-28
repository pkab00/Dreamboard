package com.vbshkn.dreamboard.data.service

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.vbshkn.dreamboard.BuildConfig
import com.vbshkn.dreamboard.domain.model.ImageEntity

class ImageDownloadService(context: Context) {
    private val downloadManager = context.getSystemService(DownloadManager::class.java)
    private val token = BuildConfig.VECTEEZY_SECRET_KEY

    fun downloadAsJpeg(imageEntity: ImageEntity): Long {
        val request = DownloadManager.Request(imageEntity.path!!.toUri())
            .setMimeType(imageEntity.format.mimeType)
            .setTitle("image_${imageEntity.id}.${imageEntity.format.formatSuffix}")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "image_${imageEntity.id}"
            )
            .addRequestHeader("Authorization", "Bearer $token")
        return downloadManager.enqueue(request)
    }
}