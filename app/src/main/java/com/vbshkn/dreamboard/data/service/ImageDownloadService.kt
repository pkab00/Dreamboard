package com.vbshkn.dreamboard.data.service

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.vbshkn.dreamboard.BuildConfig
import com.vbshkn.dreamboard.domain.model.Image

class ImageDownloadService(context: Context) {
    private val downloadManager = context.getSystemService(DownloadManager::class.java)
    private val token = BuildConfig.VECTEEZY_SECRET_KEY

    fun downloadAsJpeg(image: Image): Long {
        val request = DownloadManager.Request(image.path!!.toUri())
            .setMimeType(image.format.mimeType)
            .setTitle("image_${image.id}.${image.format.formatSuffix}")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "image_${image.id}"
            )
            .addRequestHeader("Authorization", "Bearer $token")
        return downloadManager.enqueue(request)
    }
}