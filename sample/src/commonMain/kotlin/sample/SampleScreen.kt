package sample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import io.github.alexgladkov.kviewmodel.odyssey.StoredViewModel
import sample.models.SampleEvent
import sample.models.SampleViewState
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text

@Composable
fun SampleScreen() {
    StoredViewModel(factory = { SampleViewModel() }) { viewModel ->
        val viewState by viewModel.viewStates().collectAsState()
        val viewAction by viewModel.viewActions().collectAsState(null)
        
       SampleView(viewState) { event ->
           viewModel.obtainEvent(event)
       }
    }
}

@Composable
private fun SampleView(viewState: SampleViewState, eventHandler: (SampleEvent) -> Unit) {
    Column {
        Text(text = viewState.teamName)
        Text(text = viewState.description)
    }
} 