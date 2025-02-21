package com.mustafadakhel.echo.listener.pinch

import android.view.MotionEvent
import androidx.media3.ui.PlayerView
import com.mustafadakhel.echo.listener.PlayerViewListener
import com.mustafadakhel.echo.listener.PlayerViewListenerAction
import kotlin.reflect.KClass

object LongPress : PlayerViewListener<LongPress.Data.Motion, LongPress.Data> {
    override val motionClass: KClass<out Data.Motion> = Data.Motion::class

    data class Data(
        val x: Float,
        val y: Float,
        override val motion: Motion
    ) : PlayerViewListener.Data<Data.Motion> {
        data class Motion(
            val e: MotionEvent
        ) : PlayerViewListener.Data.Motion
    }

    override fun handleEvent(
        playerView: PlayerView,
        motion: Data.Motion,
        action: PlayerViewListenerAction<Data>
    ): Boolean {
        val data = Data(motion.e.x, motion.e.y, motion)
        return action.invoke(data, playerView)
    }

    override val key: String = "LongPress"
}
