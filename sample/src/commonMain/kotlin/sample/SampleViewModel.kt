package sample

import io.github.alexgladkov.kviewmodel.BaseSharedViewModel
import sample.models.SampleAction
import sample.models.SampleEvent
import sample.models.SampleViewState

class SampleViewModel(teamName: String): BaseSharedViewModel<SampleViewState, SampleAction, SampleEvent>(
    initialState = SampleViewState(teamName = teamName, description = "$teamName the best team")
) {
    
    override fun obtainEvent(viewEvent: SampleEvent) {
        when (viewEvent) {
            SampleEvent.ShowDetails -> viewAction = SampleAction.ShowDetails
            SampleEvent.ActionInvoked -> viewAction = null
        }
    }
}