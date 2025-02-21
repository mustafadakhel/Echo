package com.mustafadakhel.echo.listener.swipe

import android.view.MotionEvent
import androidx.media3.ui.PlayerView
import com.mustafadakhel.echo.listener.PlayerViewListener
import com.mustafadakhel.echo.listener.PlayerViewListenerAction
import kotlin.reflect.KClass

object Swipe : PlayerViewListener<Swipe.Data.Motion, Swipe.Data> {
    override val motionClass: KClass<out Data.Motion> = Data.Motion::class

    data class Data(
        override val motion: Motion,
        val orientation: Orientation,
    ) : PlayerViewListener.Data<Data.Motion> {

        data class Motion(
            val e1: MotionEvent,
            val e2: MotionEvent,
            val distanceX: Float,
            val distanceY: Float
        ) : PlayerViewListener.Data.Motion

        sealed interface Orientation {
            interface Area
            data class Vertical(
                val area: Area,
            ) : Orientation {
                sealed interface Area : Orientation.Area {
                    data class Left(val distance: Float) : Area
                    data class Right(val distance: Float) : Area
                }
            }

            data class Horizontal(
                val area: Area,
            ) : Orientation {
                sealed interface Area : Orientation.Area {
                    data class Top(val distance: Float) : Area
                    data class Bottom(val distance: Float) : Area
                }
            }
        }
    }

    override fun handleEvent(
        playerView: PlayerView,
        motion: Data.Motion,
        action: PlayerViewListenerAction<Data>
    ): Boolean {
        val startPosition = motion.e1.x / playerView.width.toFloat()
        val isHorizontal = motion.distanceX > motion.distanceY
        val isTopOrLeft = startPosition < 0.5

        val orientation = if (isHorizontal) {
            val area = if (isTopOrLeft)
                Data.Orientation.Horizontal.Area.Top(motion.distanceY)
            else Data.Orientation.Horizontal.Area.Bottom(motion.distanceY)
            Data.Orientation.Horizontal(area)
        } else {
            val area = if (isTopOrLeft)
                Data.Orientation.Vertical.Area.Left(motion.distanceX)
            else Data.Orientation.Vertical.Area.Right(motion.distanceX)
            Data.Orientation.Vertical(area)
        }

        return action.invoke(
            Data(motion, orientation), playerView
        )
    }

    override val key: String = "Swipe"
}
