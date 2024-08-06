package com.mustafadakhel.echo.action.doubleTap

import androidx.media3.ui.PlayerView
import com.mustafadakhel.echo.listener.PlayerViewListenerAction
import com.mustafadakhel.echo.listener.doubleTap.DoubleTap
import com.mustafadakhel.echo.throwable.NoPlayerThrowable

class DoubleTapSeekAction(
    private val seekPeriod: Long,
) : PlayerViewListenerAction<DoubleTap.Data<DoubleTap.Bisection.Area>> {
    override fun invoke(
        data: DoubleTap.Data<DoubleTap.Bisection.Area>,
        playerView: PlayerView
    ) {
        val player = playerView.player ?: throw NoPlayerThrowable()
        when (data.area) {
            DoubleTap.Bisection.Area.Left -> {
                player.seekTo(player.currentPosition - seekPeriod)
            }

            DoubleTap.Bisection.Area.Right -> {
                player.seekTo(player.currentPosition + seekPeriod)
            }
        }
    }
}
