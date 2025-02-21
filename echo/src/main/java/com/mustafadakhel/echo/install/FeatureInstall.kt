package com.mustafadakhel.echo.install

import androidx.media3.ui.PlayerView

interface PlayerViewFeature<T : PlayerViewFeature.Config> {
    fun setup(playerView: PlayerView, block: T.() -> Unit)
    interface Config {
        val playerView: PlayerView
    }
}

fun <T : PlayerViewFeature.Config> PlayerView.add(
    feature: PlayerViewFeature<T>,
    config: T.() -> Unit
) {
    feature.setup(this, config)
}
