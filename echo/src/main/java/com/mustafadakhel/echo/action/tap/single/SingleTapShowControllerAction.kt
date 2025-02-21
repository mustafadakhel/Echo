package com.mustafadakhel.echo.action.tap.single

import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import com.mustafadakhel.echo.listener.PlayerViewListenerAction
import com.mustafadakhel.echo.listener.tap.single.SingleTap

@UnstableApi
object SingleTapShowControllerAction : PlayerViewListenerAction<SingleTap.Data> {
    override fun invoke(
        data: SingleTap.Data,
        playerView: PlayerView
    ): Boolean {
        val visible = playerView.isControllerFullyVisible
        if (visible)
            playerView.hideController()
        else playerView.showController()
        return true
    }
}
