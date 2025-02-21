package com.mustafadakhel.echo.listener.tap.dbl

import android.view.MotionEvent
import androidx.media3.ui.PlayerView
import com.mustafadakhel.echo.listener.PlayerViewListener
import com.mustafadakhel.echo.listener.PlayerViewListenerAction
import com.mustafadakhel.echo.listener.tap.dbl.DoubleTap.Data
import kotlin.reflect.KClass

sealed interface DoubleTap<D : DoubleTap.Data<out DoubleTap.Area>> :
    PlayerViewListener<DoubleTap.Data.Motion, D> {
    override val key: String get() = "DoubleTap"
    override val motionClass: KClass<out Data.Motion> get() = Data.Motion::class

    sealed interface Data<A : Area> : PlayerViewListener.Data<Data.Motion> {
        data class Motion(
            val event: MotionEvent,
        ) : PlayerViewListener.Data.Motion

        val area: A
    }

    sealed interface Area {
        val x: Float
        val y: Float
    }

    data object Trisection : DoubleTap<Trisection.Data> {
        data class Data(
            override val motion: DoubleTap.Data.Motion,
            override val area: Area
        ) : DoubleTap.Data<Area>

        sealed interface Area : DoubleTap.Area {
            data class Left(
                override val x: Float,
                override val y: Float
            ) : Area

            data class Middle(
                override val x: Float,
                override val y: Float
            ) : Area

            data class Right(
                override val x: Float,
                override val y: Float
            ) : Area
        }

        override fun handleEvent(
            playerView: PlayerView,
            motion: DoubleTap.Data.Motion,
            action: PlayerViewListenerAction<Data>
        ): Boolean {
            val tapPosition = motion.event.x / playerView.width.toFloat()
            val data = when {
                tapPosition < 0.33 -> Data(
                    motion,
                    Area.Left(motion.event.x, motion.event.y)
                )

                tapPosition < 0.66 -> Data(
                    motion,
                    Area.Middle(motion.event.x, motion.event.y)
                )

                else -> Data(
                    motion,
                    Area.Right(motion.event.x, motion.event.y)
                )
            }
            return action.invoke(data, playerView)
        }
    }

    data object Bisection : DoubleTap<Bisection.Data> {

        sealed interface Area : DoubleTap.Area {
            data class Left(
                override val x: Float,
                override val y: Float,
            ) : Area

            data class Right(
                override val x: Float,
                override val y: Float,
            ) : Area
        }

        data class Data(
            override val motion: DoubleTap.Data.Motion,
            override val area: Area
        ) : DoubleTap.Data<Area>

        override fun handleEvent(
            playerView: PlayerView,
            motion: DoubleTap.Data.Motion,
            action: PlayerViewListenerAction<Data>
        ): Boolean {
            val tapPosition = motion.event.x / playerView.width.toFloat()
            val data = when {
                tapPosition < 0.5 -> Data(
                    motion,
                    Area.Left(motion.event.x, motion.event.y)
                )

                else -> Data(
                    motion,
                    Area.Right(motion.event.x, motion.event.y)
                )
            }
            return action.invoke(data, playerView)
        }
    }

    data object Any : DoubleTap<Any.Data> {

        sealed interface Area : DoubleTap.Area {
            data class Any(
                override val x: Float,
                override val y: Float,
            ) : Area
        }

        data class Data(
            override val motion: DoubleTap.Data.Motion,
            override val area: Area
        ) : DoubleTap.Data<Area>

        override fun handleEvent(
            playerView: PlayerView,
            motion: DoubleTap.Data.Motion,
            action: PlayerViewListenerAction<Data>
        ): Boolean {
            val data = when {
                else -> Data(
                    motion,
                    Area.Any(motion.event.x, motion.event.y)
                )
            }
            return action.invoke(data, playerView)
        }
    }
}
