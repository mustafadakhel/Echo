package com.mustafadakhel.echo.listener.containers

import com.mustafadakhel.echo.listener.PlayerViewListener
import com.mustafadakhel.echo.listener.PlayerViewListenerAction
import com.mustafadakhel.echo.listener.tap.dbl.DoubleTap

class DoubleTapListenerAndAction(
    override val listener: PlayerViewListener<DoubleTap.Data.Motion, DoubleTap.Data<*>>,
    override val action: PlayerViewListenerAction<DoubleTap.Data<*>>
) : ListenerAndAction<DoubleTap.Data.Motion, DoubleTap.Data<*>>()
