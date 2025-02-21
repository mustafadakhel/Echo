package com.mustafadakhel.echo.sample.player

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.mustafadakhel.echo.action.tap.dbl.DoubleTapSeekPausePlayAction
import com.mustafadakhel.echo.action.tap.single.SingleTapShowControllerAction
import com.mustafadakhel.echo.install.add
import com.mustafadakhel.echo.listener.PlayerViewListenerFeature
import com.mustafadakhel.echo.listener.pinch.Pinch
import com.mustafadakhel.echo.listener.swipe.Swipe
import com.mustafadakhel.echo.listener.tap.dbl.DoubleTap
import com.mustafadakhel.echo.listener.tap.single.SingleTap

fun createExoPlayer(context: Context, mediaUrl: String): ExoPlayer =
    ExoPlayer.Builder(context).build().apply {
        setMediaItem(createMediaItem(mediaUrl))
        playWhenReady = true
        prepare()
    }

fun createMediaItem(url: String): MediaItem = MediaItem.fromUri(url)

fun PlayerView.bindTo(player: ExoPlayer) {
    this.player = player
}

@OptIn(UnstableApi::class)
fun PlayerView.gestures() {
    add(PlayerViewListenerFeature) {
        listen(
            DoubleTap.Trisection,
            DoubleTapSeekPausePlayAction(10000)
        )
        listen(
            SingleTap,
            SingleTapShowControllerAction
        )
        listen(Swipe, ::seekWithSwipe)
        listen(Pinch) { data, playerView ->
            // TODO: 11/08/2023 add pinch action
            // Dummy pinch action
            playerView.videoSurfaceView?.scaleX = data.scale
            playerView.videoSurfaceView?.scaleY = data.scale
            true
        }
    }
}

@OptIn(UnstableApi::class)
fun seekWithSwipe(data: Swipe.Data, playerView: PlayerView): Boolean {
    return playerView.player?.let { player ->
        playerView.showController()
        val currentMs = player.currentPosition
        val durationMs = player.duration
        val seekMs = currentMs + data.motion.distanceX.toLong() * -100
        player.seekTo(
            seekMs.coerceIn(0, durationMs)
        )
        true
    } ?: false
}
