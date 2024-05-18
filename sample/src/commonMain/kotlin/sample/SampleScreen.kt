package sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.alexgladkov.kviewmodel.odyssey.StoredViewModel
import navigation.SampleNavigation
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import sample.models.SampleAction
import sample.models.SampleEvent
import sample.models.SampleViewState

@Composable
fun SampleScreen(teamName: String) {
    val rootController = LocalRootController.current

    StoredViewModel(factory = { SampleViewModel(teamName) }) { viewModel ->
        val viewState by viewModel.viewStates().collectAsState()
        val viewAction by viewModel.viewActions().collectAsState(null)
        
        SampleView(viewState) { event ->
           viewModel.obtainEvent(event)
        }

        when (viewAction) {
            SampleAction.ShowDetails -> {
                rootController.push(SampleNavigation.sampleDetailScreen)
                viewModel.obtainEvent(SampleEvent.ActionInvoked)
            }
            null -> { }
        }
    }
}

@Composable
private fun SampleView(viewState: SampleViewState, eventHandler: (SampleEvent) -> Unit) {
    Column(modifier = Modifier.padding(16.dp).windowInsetsPadding(WindowInsets.systemBars)) {
        Text(text = viewState.teamName, fontSize = 28.sp, fontWeight = Medium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = viewState.description)
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = {
            eventHandler.invoke(SampleEvent.ShowDetails)
        }) {
            Text("Show details")
        }
    }
} 