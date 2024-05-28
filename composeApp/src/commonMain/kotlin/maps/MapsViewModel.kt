package maps

import io.github.alexgladkov.kviewmodel.BaseSharedViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class MapsViewModel : BaseSharedViewModel<MapsViewState, Nothing, MapsEvent>(MapsViewState()) {
    private val store = Store(MapsState(), ::reduce)

    init {
        withViewModelScope {
            store.states()
                .map { MapsViewState() } //TODO
                .onEach { viewState = it }
                .launchIn(this)
        }
    }

    override fun obtainEvent(viewEvent: MapsEvent) {

    }
}