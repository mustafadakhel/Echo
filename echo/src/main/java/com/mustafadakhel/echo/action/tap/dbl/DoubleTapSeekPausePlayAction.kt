package com.mustafadakhel.echo.action.tap.dbl

import android.util.Log
import androidx.media3.ui.PlayerView
import com.mustafadakhel.echo.listener.PlayerViewListenerAction
import com.mustafadakhel.echo.listener.tap.dbl.DoubleTap
import com.mustafadakhel.echo.throwable.NoPlayerThrowable

class DoubleTapSeekPausePlayAction(
    private val seekPeriod: Long,
) : PlayerViewListenerAction<DoubleTap.Trisection.Data> {
    override fun invoke(
        data: DoubleTap.Trisection.Data,
        playerView: PlayerView
    ): Boolean {
        Log.d("DoubleTapSeekPausePlayAction", "invoke")
        val player = playerView.player ?: throw NoPlayerThrowable()
        when (data.area) {
            is DoubleTap.Trisection.Area.Left -> {
                player.seekTo(player.currentPosition - seekPeriod)
            }

            is DoubleTap.Trisection.Area.Middle -> {
                player.playWhenReady = player.playWhenReady.not()
            }

            is DoubleTap.Trisection.Area.Right -> {
                player.seekTo(player.currentPosition + seekPeriod)
            }
        }
        return true
    }
}
