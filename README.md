# Echo

A WIP library that adds some useful extensions for easily adding features to ExoPlayer utilizing the Kotlin DSL

```kotlin
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
```
