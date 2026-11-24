package com.nexatv.app.download

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.offline.DownloadRequest
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.offline.DownloadService
import java.net.URI

object DownloadHelper {

    fun startDownload(
        context: Context,
        streamUrl: String,
        title: String
    ) {
        val contentId = streamUrl.hashCode().toString()

        val request = DownloadRequest.Builder(
            contentId,
            URI.create(streamUrl)
        ).setData(title.toByteArray()).build()

        DownloadService.sendAddDownload(
            context,
            DownloadService::class.java,
            request,
            false
        )
    }

    fun getDownloadStatus(
        context: Context,
        streamUrl: String
    ): Int {
        val contentId = streamUrl.hashCode().toString()
        val manager = DownloadManager(context, DatabaseProvider, null, null, null)
        return manager.downloadIndex.getDownload(contentId)?.state ?: 0
    }

    fun isDownloaded(
        context: Context,
        streamUrl: String
    ): Boolean {
        return getDownloadStatus(context, streamUrl) == DownloadManager.STATE_COMPLETED
    }

    fun isDownloading(
        context: Context,
        streamUrl: String
    ): Boolean {
        return getDownloadStatus(context, streamUrl) == DownloadManager.STATE_DOWNLOADING
    }
}
