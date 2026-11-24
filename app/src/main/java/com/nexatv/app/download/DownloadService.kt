package com.nexatv.app.download

import android.app.Notification
import android.content.Context
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.offline.DownloadService
import androidx.media3.exoplayer.offline.DownloadService.startForeground
import androidx.media3.exoplayer.scheduler.PlatformScheduler
import androidx.media3.exoplayer.offline.DownloadRequest

@UnstableApi
class DownloadService : DownloadService(
    FOREGROUND_NOTIFICATION_ID,
    DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL,
    DownloadService.CHANNEL_ID,
    0,
    0
) {

    override fun getDownloadManager() =
        DownloadHelper.getDownloadManager(this)

    override fun getScheduler() =
        PlatformScheduler(this, JOB_ID)

    override fun getForegroundNotification(
        downloads: MutableList<DownloadRequest>
    ): Notification {
        return startForeground(
            this,
            downloads,
            FOREGROUND_NOTIFICATION_ID
        )
    }

    companion object {
        private const val FOREGROUND_NOTIFICATION_ID = 1
        private const val JOB_ID = 1
        private const val DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL = 5000L
    }
}
