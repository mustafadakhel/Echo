package com.mustafadakhel.echo.listener.doubleTap

import android.view.MotionEvent
import androidx.media3.ui.PlayerView
import com.mustafadakhel.echo.listener.PlayerViewListener
import com.mustafadakhel.echo.listener.PlayerViewListenerAction

sealed interface DoubleTap<D : DoubleTap.Data<out DoubleTap.Area>> : PlayerViewListener<D> {
    sealed interface Data<A : Area> : PlayerViewListener.Data {
        val area: A
    }

    sealed interface Area

    data object Trisection : DoubleTap<Trisection.Data> {

        data class Data(override val area: Area) : DoubleTap.Data<Area>

        sealed interface Area : DoubleTap.Area {
            data object Left : Area
            data object Middle : Area
            data object Right : Area
        }

        override fun installFor(playerView: PlayerView, onData: PlayerViewListenerAction<Data>) {
            playerView.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    val tapPosition = event.x / playerView.width.toFloat()
                    val data = when {
                        tapPosition < 0.33 -> Data(Area.Left)
                        tapPosition < 0.66 -> Data(Area.Middle)
                        else -> Data(Area.Right)
                    }
                    onData(data, playerView)
                } else if (event.action == MotionEvent.ACTION_UP) {
                    playerView.performClick()
                }
                false
            }
        }
    }

    data object Bisection : DoubleTap<Bisection.Data> {

        sealed interface Area : DoubleTap.Area {
            data object Left : Area
            data object Right : Area
        }

        data class Data(override val area: Area) : DoubleTap.Data<Area>

        override fun installFor(playerView: PlayerView, onData: PlayerViewListenerAction<Data>) {
            playerView.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    val tapPosition = event.x / playerView.width.toFloat()
                    val data = when {
                        tapPosition < 0.5 -> Data(Area.Left)
                        else -> Data(Area.Right)
                    }
                    onData(data, playerView)
                } else if (event.action == MotionEvent.ACTION_UP) {
                    playerView.performClick()
                }
                false
            }
        }
    }
}
