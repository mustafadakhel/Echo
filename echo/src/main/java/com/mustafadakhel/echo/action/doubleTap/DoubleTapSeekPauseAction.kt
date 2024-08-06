package com.mustafadakhel.echo.action.doubleTap

import androidx.media3.ui.PlayerView
import com.mustafadakhel.echo.listener.PlayerViewListenerAction
import com.mustafadakhel.echo.listener.doubleTap.DoubleTap
import com.mustafadakhel.echo.throwable.NoPlayerThrowable

class DoubleTapSeekPauseAction(
    private val seekPeriod: Long,
) : PlayerViewListenerAction<DoubleTap.Data<DoubleTap.Trisection.Area>> {
    override fun invoke(
        data: DoubleTap.Data<DoubleTap.Trisection.Area>,
        playerView: PlayerView
    ) {
        val player = playerView.player ?: throw NoPlayerThrowable()
        when (data.area) {
            DoubleTap.Trisection.Area.Left -> {
                player.seekTo(player.currentPosition - seekPeriod)
            }

            DoubleTap.Trisection.Area.Middle -> {
                player.playWhenReady = player.playWhenReady.not()
            }

            DoubleTap.Trisection.Area.Right -> {
                player.seekTo(player.currentPosition + seekPeriod)
            }
        }
    }
}
