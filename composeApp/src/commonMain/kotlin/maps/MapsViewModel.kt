package maps

import io.github.alexgladkov.kviewmodel.BaseSharedViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import maps.udf.MapScreen
import maps.udf.MapsEvent
import maps.udf.MapsState
import maps.udf.Store
import maps.udf.reduce

class MapsViewModel private constructor(initialState: MapsState) :
    BaseSharedViewModel<MapViewState, Nothing, MapsEvent>(initialState.toViewState()) {

    constructor() : this(initialState = MapsState())

    private val store = Store(initialState, ::reduce)

    init {
        withViewModelScope {
            store.states()
                .map { state -> state.toViewState() }
                .onEach { viewState = it }
                .launchIn(this)
        }
    }

    override fun obtainEvent(viewEvent: MapsEvent) {
        withViewModelScope {
            store.dispatch(viewEvent)
        }
    }
}

private fun MapsState.toViewState() = when (screen) {
    is MapScreen.ActualFun -> MapPlayWithMercatorViewState(screen.referencePoint, screen.contour)
    is MapScreen.ExpectFun -> MapEditContourViewState(
        screen.contour,
        isMapDraggable = screen.mode == MapScreen.ExpectFun.Mode.DragMap,
        showRevertButton = screen.contour.parts.isNotEmpty(),
    )
}