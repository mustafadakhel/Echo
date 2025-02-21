@file:Suppress("UNCHECKED_CAST")

package com.mustafadakhel.echo.listener

import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.media3.ui.PlayerView
import com.mustafadakhel.echo.install.PlayerViewFeature
import com.mustafadakhel.echo.listener.containers.DoubleTapListenerAndAction
import com.mustafadakhel.echo.listener.containers.ListenerAndAction
import com.mustafadakhel.echo.listener.containers.LongPressListenerAndAction
import com.mustafadakhel.echo.listener.containers.PinchListenerAndAction
import com.mustafadakhel.echo.listener.containers.SingleTapListenerAndAction
import com.mustafadakhel.echo.listener.containers.SwipeListenerAndAction
import com.mustafadakhel.echo.listener.pinch.LongPress
import com.mustafadakhel.echo.listener.pinch.Pinch
import com.mustafadakhel.echo.listener.swipe.Swipe
import com.mustafadakhel.echo.listener.tap.dbl.DoubleTap
import com.mustafadakhel.echo.listener.tap.single.SingleTap
import kotlin.reflect.KClass

internal interface ListenerFactory<D : PlayerViewListener.Data<*>> {
    fun create(
        listener: PlayerViewListener<*, D>,
        action: PlayerViewListenerAction<D>
    ): ListenerAndAction<*, D>
}

object PlayerViewListenerFeature : PlayerViewFeature<PlayerViewListenerFeature.Config> {

    class Config(override val playerView: PlayerView) : PlayerViewFeature.Config {

        internal val listeners = mutableListOf<ListenerAndAction<*, *>>()
        private val listenerRegistry =
            mutableMapOf<KClass<out PlayerViewListener.Data.Motion>, ListenerFactory<*>>()

        internal fun <D : PlayerViewListener.Data<*>> registerListenerFactory(
            motionClass: KClass<out PlayerViewListener.Data.Motion>,
            factory: ListenerFactory<D>
        ) {
            listenerRegistry[motionClass] = factory
        }

        fun <D : PlayerViewListener.Data<*>> listen(
            listener: PlayerViewListener<*, D>,
            action: PlayerViewListenerAction<D>
        ) {
            val factory = listenerRegistry[listener.motionClass] as? ListenerFactory<D>
                ?: throw IllegalArgumentException("No factory registered for listener: ${listener.key}")

            listeners.add(factory.create(listener, action))
        }
    }

    override fun setup(playerView: PlayerView, block: Config.() -> Unit) {
        val config = Config(playerView).apply { registerDefaultListeners() }.apply(block)

        val echoGestureListener = EchoGestureListener(playerView, config.listeners)
        val echoScaleGestureListener = EchoScaleGestureListener(playerView, config.listeners)
        val detector = GestureDetector(playerView.context, echoGestureListener)
        val scaleGestureDetector =
            ScaleGestureDetector(playerView.context, echoScaleGestureListener)

        playerView.setOnTouchListener { v, event ->
            var handled = detector.onTouchEvent(event) || scaleGestureDetector.onTouchEvent(event)
            if (!handled && event.action == MotionEvent.ACTION_UP) handled = v.performClick()
            handled
        }
    }

    private fun Config.registerDefaultListeners() {
        registerListenerFactory(SingleTap.Data.Motion::class, SingleTapListenerFactory)
        registerListenerFactory(DoubleTap.Data.Motion::class, DoubleTapListenerFactory)
        registerListenerFactory(Swipe.Data.Motion::class, SwipeListenerFactory)
        registerListenerFactory(Pinch.Data.Motion::class, PinchListenerFactory)
        registerListenerFactory(LongPress.Data.Motion::class, LongPressListenerFactory)
    }
}

private object SingleTapListenerFactory : ListenerFactory<SingleTap.Data> {
    override fun create(
        listener: PlayerViewListener<*, SingleTap.Data>,
        action: PlayerViewListenerAction<SingleTap.Data>
    ): ListenerAndAction<*, SingleTap.Data> {
        return SingleTapListenerAndAction(
            listener as PlayerViewListener<SingleTap.Data.Motion, SingleTap.Data>,
            action
        )
    }
}

private object DoubleTapListenerFactory : ListenerFactory<DoubleTap.Data<*>> {
    override fun create(
        listener: PlayerViewListener<*, DoubleTap.Data<*>>,
        action: PlayerViewListenerAction<DoubleTap.Data<*>>
    ): ListenerAndAction<*, DoubleTap.Data<*>> {
        return DoubleTapListenerAndAction(
            listener as PlayerViewListener<DoubleTap.Data.Motion, DoubleTap.Data<*>>,
            action
        )
    }
}

private object SwipeListenerFactory : ListenerFactory<Swipe.Data> {
    override fun create(
        listener: PlayerViewListener<*, Swipe.Data>,
        action: PlayerViewListenerAction<Swipe.Data>
    ): ListenerAndAction<*, Swipe.Data> {
        return SwipeListenerAndAction(
            listener as PlayerViewListener<Swipe.Data.Motion, Swipe.Data>,
            action
        )
    }
}

private object PinchListenerFactory : ListenerFactory<Pinch.Data> {
    override fun create(
        listener: PlayerViewListener<*, Pinch.Data>,
        action: PlayerViewListenerAction<Pinch.Data>
    ): ListenerAndAction<*, Pinch.Data> {
        return PinchListenerAndAction(
            listener as PlayerViewListener<Pinch.Data.Motion, Pinch.Data>,
            action
        )
    }
}


private object LongPressListenerFactory : ListenerFactory<LongPress.Data> {
    override fun create(
        listener: PlayerViewListener<*, LongPress.Data>,
        action: PlayerViewListenerAction<LongPress.Data>
    ): ListenerAndAction<*, LongPress.Data> {
        return LongPressListenerAndAction(
            listener as PlayerViewListener<LongPress.Data.Motion, LongPress.Data>,
            action
        )
    }
}

interface PlayerViewListener<M : PlayerViewListener.Data.Motion, T : PlayerViewListener.Data<M>> {
    val motionClass: KClass<out M>
    val key: String

    fun handleEvent(
        playerView: PlayerView,
        motion: M,
        action: PlayerViewListenerAction<T>
    ): Boolean

    interface Data<M : Data.Motion> {
        val motion: M

        interface Motion
    }
}
