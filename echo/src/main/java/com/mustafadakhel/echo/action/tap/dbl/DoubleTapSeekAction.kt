package com.mustafadakhel.echo.action.tap.dbl

import androidx.media3.ui.PlayerView
import com.mustafadakhel.echo.listener.PlayerViewListenerAction
import com.mustafadakhel.echo.listener.tap.dbl.DoubleTap
import com.mustafadakhel.echo.throwable.NoPlayerThrowable

class DoubleTapSeekAction(
    private val seekPeriod: Long,
) : PlayerViewListenerAction<DoubleTap.Bisection.Data> {
    override fun invoke(
        data: DoubleTap.Bisection.Data,
        playerView: PlayerView
    ): Boolean {
        val player = playerView.player ?: throw NoPlayerThrowable()
        when (data.area) {
            is DoubleTap.Bisection.Area.Left -> {
                player.seekTo(player.currentPosition - seekPeriod)
            }

            is DoubleTap.Bisection.Area.Right -> {
                player.seekTo(player.currentPosition + seekPeriod)
            }
        }
        return true
    }
}
