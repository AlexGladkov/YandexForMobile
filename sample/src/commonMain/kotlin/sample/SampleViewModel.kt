package sample

import io.github.alexgladkov.kviewmodel.BaseSharedViewModel
import sample.models.SampleAction
import sample.models.SampleEvent
import sample.models.SampleViewState

class SampleViewModel(): BaseSharedViewModel<SampleViewState, SampleAction, SampleEvent>(
    initialState = SampleViewState()
) {
    
    override fun obtainEvent(viewEvent: SampleEvent) {
        
    }
}