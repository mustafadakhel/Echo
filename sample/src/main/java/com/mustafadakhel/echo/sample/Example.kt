package com.mustafadakhel.echo.sample

import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import com.mustafadakhel.echo.action.tap.dbl.DoubleTapPausePlayAction
import com.mustafadakhel.echo.action.tap.dbl.DoubleTapSeekPausePlayAction
import com.mustafadakhel.echo.action.tap.single.SingleTapShowControllerAction
import com.mustafadakhel.echo.install.add
import com.mustafadakhel.echo.listener.PlayerViewListenerFeature
import com.mustafadakhel.echo.listener.swipe.Swipe
import com.mustafadakhel.echo.listener.tap.dbl.DoubleTap
import com.mustafadakhel.echo.listener.tap.single.SingleTap

@OptIn(UnstableApi::class)
fun PlayerView.example() {
    // custom actions for a certain listener
    add(PlayerViewListenerFeature) {
        listen(DoubleTap.Bisection) { data, player ->
            // do something custom
            true
        }
        listen(Swipe) { data, player ->
            // do something custom
            true
        }
    }

    // default actions
    add(PlayerViewListenerFeature) {
        listen(
            DoubleTap.Trisection,
            DoubleTapSeekPausePlayAction(10000)
        )
        listen(
            DoubleTap.Any,
            DoubleTapPausePlayAction
        )
        // or ..
        listen(
            SingleTap,
            SingleTapShowControllerAction
        )
    }
}
