package com.mustafadakhel.echo.listener

import androidx.media3.ui.PlayerView
import com.mustafadakhel.echo.install.PlayerViewFeature

typealias PlayerViewListenerAction<T> = (data: T, playerView: PlayerView) -> Unit

object PlayerViewListenerFeature : PlayerViewFeature<PlayerViewListenerFeature.Config> {

    class Config(
        override val playerView: PlayerView
    ) : PlayerViewFeature.Config {
        fun <T : PlayerViewListener.Data> listen(
            listener: PlayerViewListener<T>,
            action: PlayerViewListenerAction<T>
        ) {
            listener.installFor(playerView = playerView, onData = action)
        }
    }

    override fun setup(playerView: PlayerView, config: Config.() -> Unit) {
        Config(playerView).apply(config)
    }
}

interface PlayerViewListener<T : PlayerViewListener.Data> {
    fun installFor(playerView: PlayerView, onData: PlayerViewListenerAction<T>)
    interface Data
}
