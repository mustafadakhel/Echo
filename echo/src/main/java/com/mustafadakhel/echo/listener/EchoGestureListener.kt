@file:Suppress("UNCHECKED_CAST")

package com.mustafadakhel.echo.listener

import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.media3.ui.PlayerView
import com.mustafadakhel.echo.listener.containers.ListenerAndAction
import com.mustafadakhel.echo.listener.containers.PinchListenerAndAction
import com.mustafadakhel.echo.listener.pinch.LongPress
import com.mustafadakhel.echo.listener.pinch.Pinch
import com.mustafadakhel.echo.listener.swipe.Swipe
import com.mustafadakhel.echo.listener.tap.dbl.DoubleTap
import com.mustafadakhel.echo.listener.tap.single.SingleTap
import kotlin.reflect.KClass

internal class ListenerDispatcher(
    private val playerView: PlayerView,
    listeners: List<ListenerAndAction<*, *>>
) {

    private val registry: Map<KClass<*>, List<ListenerAndAction<*, *>>> =
        listeners.groupBy { it.listener.motionClass }

    fun <M : PlayerViewListener.Data.Motion> dispatch(motion: M): Boolean {
        val listeners = registry[motion::class] ?: return false
        return listeners.any { (it as ListenerAndAction<M, *>).handleEvent(playerView, motion) }
    }
}

internal class EchoGestureListener(
    playerView: PlayerView,
    listeners: List<ListenerAndAction<*, *>>
) : GestureDetector.SimpleOnGestureListener() {

    private val dispatcher = ListenerDispatcher(playerView, listeners)

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        return dispatcher.dispatch(SingleTap.Data.Motion(e))
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        return dispatcher.dispatch(DoubleTap.Data.Motion(e))
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return e1?.let {
            dispatcher.dispatch(Swipe.Data.Motion(e1, e2, distanceX, distanceY))
        } ?: super.onScroll(null, e2, distanceX, distanceY)
    }

    override fun onLongPress(e: MotionEvent) {
        dispatcher.dispatch(LongPress.Data.Motion(e))
    }
}

internal class EchoScaleGestureListener(
    private val playerView: PlayerView,
    listeners: List<ListenerAndAction<*, *>>
) : ScaleGestureDetector.SimpleOnScaleGestureListener() {
    private val pinchListeners =
        listeners.filterIsInstance<PinchListenerAndAction>()

    override fun onScale(detector: ScaleGestureDetector): Boolean {
        val scale = detector.scaleFactor
        val motion = Pinch.Data.Motion(scale)
        return pinchListeners.any { it.handleEvent(playerView, motion) }
    }
}
