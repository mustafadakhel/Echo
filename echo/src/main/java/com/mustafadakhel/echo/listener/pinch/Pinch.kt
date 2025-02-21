package com.mustafadakhel.echo.listener.pinch

import androidx.media3.ui.PlayerView
import com.mustafadakhel.echo.listener.PlayerViewListener
import com.mustafadakhel.echo.listener.PlayerViewListenerAction
import kotlin.reflect.KClass

object Pinch : PlayerViewListener<Pinch.Data.Motion, Pinch.Data> {
    override val motionClass: KClass<out Data.Motion> = Data.Motion::class
    data class Data(
        val scale: Float,
        override val motion: Motion
    ) : PlayerViewListener.Data<Data.Motion> {
        data class Motion(
            val scale: Float,
        ) : PlayerViewListener.Data.Motion
    }

    override fun handleEvent(
        playerView: PlayerView,
        motion: Data.Motion,
        action: PlayerViewListenerAction<Data>
    ): Boolean {
        val data = Data(motion.scale, motion)
        return action.invoke(data, playerView)
    }

    override val key: String = "Pinch"
}
