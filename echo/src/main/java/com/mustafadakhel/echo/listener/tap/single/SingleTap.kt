package com.mustafadakhel.echo.listener.tap.single

import android.view.MotionEvent
import androidx.media3.ui.PlayerView
import com.mustafadakhel.echo.listener.PlayerViewListener
import com.mustafadakhel.echo.listener.PlayerViewListenerAction
import kotlin.reflect.KClass

object SingleTap : PlayerViewListener<SingleTap.Data.Motion, SingleTap.Data> {
    override val motionClass: KClass<out Data.Motion> = Data.Motion::class

    data class Data(
        override val motion: Motion
    ) : PlayerViewListener.Data<Data.Motion> {

        data class Motion(
            val event: MotionEvent,
        ) : PlayerViewListener.Data.Motion
    }

    override fun handleEvent(
        playerView: PlayerView,
        motion: Data.Motion,
        action: PlayerViewListenerAction<Data>
    ): Boolean {
        return action.invoke(Data(motion), playerView)
    }

    override val key: String = "SingleTap"
}
