package com.mustafadakhel.echo.listener.containers

import com.mustafadakhel.echo.listener.PlayerViewListener
import com.mustafadakhel.echo.listener.PlayerViewListenerAction
import com.mustafadakhel.echo.listener.pinch.LongPress

class LongPressListenerAndAction(
    override val listener: PlayerViewListener<LongPress.Data.Motion, LongPress.Data>,
    override val action: PlayerViewListenerAction<LongPress.Data>
) : ListenerAndAction<LongPress.Data.Motion, LongPress.Data>()
