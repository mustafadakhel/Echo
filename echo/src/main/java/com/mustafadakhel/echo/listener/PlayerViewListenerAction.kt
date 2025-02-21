package com.mustafadakhel.echo.listener

import androidx.media3.ui.PlayerView

fun interface PlayerViewListenerAction<T : PlayerViewListener.Data<out PlayerViewListener.Data.Motion>> {
    fun invoke(data: T, playerView: PlayerView): Boolean
}
