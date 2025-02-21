package com.mustafadakhel.echo.listener.containers

import com.mustafadakhel.echo.listener.PlayerViewListener
import com.mustafadakhel.echo.listener.PlayerViewListenerAction
import com.mustafadakhel.echo.listener.tap.single.SingleTap

class SingleTapListenerAndAction(
    override val listener: PlayerViewListener<SingleTap.Data.Motion, SingleTap.Data>,
    override val action: PlayerViewListenerAction<SingleTap.Data>
) : ListenerAndAction<SingleTap.Data.Motion, SingleTap.Data>()
