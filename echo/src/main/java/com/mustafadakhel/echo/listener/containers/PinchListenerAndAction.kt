package com.mustafadakhel.echo.listener.containers

import com.mustafadakhel.echo.listener.PlayerViewListener
import com.mustafadakhel.echo.listener.PlayerViewListenerAction
import com.mustafadakhel.echo.listener.pinch.Pinch

class PinchListenerAndAction(
    override val listener: PlayerViewListener<Pinch.Data.Motion, Pinch.Data>,
    override val action: PlayerViewListenerAction<Pinch.Data>
) : ListenerAndAction<Pinch.Data.Motion, Pinch.Data>()
