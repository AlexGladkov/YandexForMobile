package sample.models

sealed class SampleEvent {
    data object ShowDetails : SampleEvent()
    data object ActionInvoked : SampleEvent()
}