package com.mustafadakhel.echo.action.tap.dbl

import androidx.media3.ui.PlayerView
import com.mustafadakhel.echo.listener.PlayerViewListenerAction
import com.mustafadakhel.echo.listener.tap.dbl.DoubleTap
import com.mustafadakhel.echo.throwable.NoPlayerThrowable

object DoubleTapPausePlayAction : PlayerViewListenerAction<DoubleTap.Any.Data> {
    override fun invoke(
        data: DoubleTap.Any.Data,
        playerView: PlayerView
    ): Boolean {
        val player = playerView.player ?: throw NoPlayerThrowable()
        player.playWhenReady = player.playWhenReady.not()
        return true
    }
}
