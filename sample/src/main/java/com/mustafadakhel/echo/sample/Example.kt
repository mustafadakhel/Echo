package com.mustafadakhel.echo.sample

import androidx.media3.ui.PlayerView
import com.mustafadakhel.echo.action.doubleTap.DoubleTapSeekAction
import com.mustafadakhel.echo.action.doubleTap.DoubleTapSeekPauseAction
import com.mustafadakhel.echo.install.install
import com.mustafadakhel.echo.listener.PlayerViewListenerFeature
import com.mustafadakhel.echo.listener.doubleTap.DoubleTap

fun PlayerView.example() {
    // custom actions for a certain listener
    install(PlayerViewListenerFeature) {
        listen(DoubleTap.Bisection) { data, player ->
            // do something custom
        }
    }

    // default actions
    install(PlayerViewListenerFeature) {
        listen(
            DoubleTap.Trisection,
            DoubleTapSeekPauseAction(10000)
        )
    }
    install(PlayerViewListenerFeature) {
        listen(
            DoubleTap.Bisection,
            DoubleTapSeekAction(10000)
        )
    }
}
