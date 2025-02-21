package com.mustafadakhel.echo.listener.containers

import androidx.media3.ui.PlayerView
import com.mustafadakhel.echo.listener.PlayerViewListener
import com.mustafadakhel.echo.listener.PlayerViewListenerAction

abstract class ListenerAndAction<M : PlayerViewListener.Data.Motion, D : PlayerViewListener.Data<M>> {
    abstract val listener: PlayerViewListener<M, D>
    abstract val action: PlayerViewListenerAction<D>
    fun handleEvent(playerView: PlayerView, motion: M): Boolean {
        return listener.handleEvent(playerView, motion, action)
    }
}
