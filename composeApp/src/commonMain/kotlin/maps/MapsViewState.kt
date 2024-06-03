package maps


data class MapsViewState(
    val isMapDraggable: Boolean = true,
    val screen: MapsScreen = MapsScreen.Fun.ExpectFun(),
)