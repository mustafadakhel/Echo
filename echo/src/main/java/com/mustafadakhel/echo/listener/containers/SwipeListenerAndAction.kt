package com.mustafadakhel.echo.listener.containers

import com.mustafadakhel.echo.listener.PlayerViewListener
import com.mustafadakhel.echo.listener.PlayerViewListenerAction
import com.mustafadakhel.echo.listener.swipe.Swipe

class SwipeListenerAndAction(
    override val listener: PlayerViewListener<Swipe.Data.Motion, Swipe.Data>,
    override val action: PlayerViewListenerAction<Swipe.Data>
) : ListenerAndAction<Swipe.Data.Motion, Swipe.Data>()
