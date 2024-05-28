package maps

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

interface Action

class Store<TState>(initialState: TState, reduce: (TState, Action) -> TState) {
    private val statesSubj = MutableStateFlow(initialState)

    private val actionsSubj = MutableSharedFlow<Action>()

    private val scope = MainScope()

    init {
        actionsSubj
            .onEach { action ->
                emitState(reduce(currentState, action))
            }.launchIn(scope)
    }

    fun states(): Flow<TState> = statesSubj

    fun dispatchAsync(action: Action) {
        scope.launch {
            dispatch(action)
        }
    }

    suspend fun dispatch(action: Action) {
        actionsSubj.emit(action)
    }

    private val currentState: TState
        get() = statesSubj.value

    private suspend fun emitState(state: TState) {
        statesSubj.emit(state)
    }
}