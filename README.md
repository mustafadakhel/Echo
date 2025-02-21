# Echo

A WIP library that adds some useful extensions for easily adding features to ExoPlayer utilizing the Kotlin DSL

```kotlin
fun PlayerView.example() {
    // custom actions for a certain listener
    install(PlayerViewListenerFeature) {
        listen(DoubleTap.Bisection) { data, player ->
            // do something custom
            true
        }
    }

    // default actions
    install(PlayerViewListenerFeature) {
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

```
