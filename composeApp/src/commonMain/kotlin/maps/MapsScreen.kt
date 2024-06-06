@file:OptIn(ExperimentalResourceApi::class)

package maps

import MapsConfig
import PlatformConfiguration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import di.Inject
import io.github.alexgladkov.kviewmodel.odyssey.StoredViewModel
import maps.bindings.Coordinates
import maps.bindings.DragsPositionsListener
import maps.bindings.GeoMapObjectCollection
import maps.bindings.GeoMapObjectDragListener
import maps.bindings.GeoPlacemark
import maps.bindings.GeoPlacemarkImage
import maps.bindings.GeoPoint
import maps.bindings.compose.Map
import maps.bindings.compose.MapState
import maps.bindings.point
import maps.udf.AddPoint
import maps.udf.BeginEditContourPart
import maps.udf.EditableContour
import maps.udf.EndEditContourPart
import maps.udf.SwitchToDragMap
import maps.udf.SwitchToDrawContour
import maps.udf.GoToActualFun
import maps.udf.GoToExpectFun
import maps.udf.MapScreen
import maps.udf.MapsEvent
import maps.udf.RelativeContour
import maps.udf.RevertLastContourPart
import maps.utils.DirectProblemSolver
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.vectorResource
import org.kodein.di.instance
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import worlds.composeapp.generated.resources.Res
import worlds.composeapp.generated.resources.map_control_button_drag
import worlds.composeapp.generated.resources.map_control_button_draw


@Composable
fun MapsScreen() {
    MapFunScreen()
}

@Composable
expect fun MapsConfig.dotImage(): GeoPlacemarkImage

@Composable
expect fun MapsConfig.touchAreaImage(): GeoPlacemarkImage

class MapScreenMutableState(
    val dotImage: GeoPlacemarkImage,
    val areaImage: GeoPlacemarkImage,
) {
    val mapState by mutableStateOf(MapState())
    var savedCollection by mutableStateOf<GeoMapObjectCollection?>(null)
    var savedTouchAreaPlacemark by mutableStateOf<GeoPlacemark?>(null)
    val config by derivedStateOf {
        Inject.di.instance<PlatformConfiguration>().mapsConfig()
    }

    // We need to hold strong reference to listener because of WeakRefs in implementations
    var touchAreaDragListener by mutableStateOf<GeoMapObjectDragListener?>(null)

    val contourPlacemarks by mutableStateOf(mutableListOf<GeoPlacemark>())

    fun touchAreaPlacemark(): GeoPlacemark {
        return savedTouchAreaPlacemark
            ?: savedCollection().addPlacemark().also { savedTouchAreaPlacemark = it }
    }

    fun savedCollection(): GeoMapObjectCollection {
        return savedCollection
            ?: (mapState.map!!.addCollection().also { savedCollection = it })
    }

    fun moveRelativeContourByTouchArea(contour: RelativeContour) {

        moveRelativeContour(
            touchAreaPlacemark().getGeometry(),
            contour,
            contourPlacemarks,
            savedCollection(),
            dotImage,
        )
    }
}

@Immutable
private data class ImmutableConfig(val impl: MapsConfig)

@Composable
private fun MapFunScreen() {
    val config by derivedStateOf {
        ImmutableConfig(Inject.di.instance<PlatformConfiguration>().mapsConfig())
    }
    val dotImage = config.impl.dotImage()
    val touchAreaImage = config.impl.touchAreaImage()
    val mapsScreenMutableState = remember {
        MapScreenMutableState(
            dotImage,
            touchAreaImage,
        )
    }

    StoredViewModel(factory = { MapsViewModel() }) { viewModel ->
        Box {
            val viewState by viewModel.viewStates().collectAsState()
            Map(
                state = mapsScreenMutableState.mapState,
                onCameraMoved = {
                    (viewState as? MapPlayWithMercatorViewState)?.run {
                        mapsScreenMutableState.moveRelativeContourByTouchArea(contour)
                    }
                },
            )

            when (val vs = viewState) {
                is MapPlayWithMercatorViewState -> PlayWithMercatorScreen(
                    mapsScreenMutableState,
                    viewState = vs,
                    dispatch = { event -> viewModel.obtainEvent(event) },
                )

                is MapEditContourViewState -> EditContourScreen(
                    mapsScreenMutableState,
                    viewState = vs,
                    dispatch = { event -> viewModel.obtainEvent(event) },
                )
            }

            CloseButton(Modifier.align(Alignment.BottomStart))
        }
    }
}

@Composable
private fun PlayWithMercatorScreen(
    mapsScreenState: MapScreenMutableState,
    viewState: MapPlayWithMercatorViewState,
    dispatch: (MapsEvent) -> Unit,
) = Box(modifier = Modifier.fillMaxSize()) {

    MapControlButton(
        icon = Icons.Default.Edit,
        onClick = { dispatch(GoToExpectFun()) },
        modifier = Modifier.align(Alignment.BottomEnd),
    )

    LaunchedEffect(Unit) {
        mapsScreenState.touchAreaPlacemark().let { touchArea ->
            val listener = DragsPositionsListener {
                mapsScreenState.moveRelativeContourByTouchArea(viewState.contour)
            }
            mapsScreenState.touchAreaDragListener = listener
            with(touchArea) {
                setDragListener(listener)
                setDraggable(true)
                setIcon(mapsScreenState.areaImage)
            }
        }
    }

    SideEffect {
        with(mapsScreenState) {
            touchAreaPlacemark().run {
                setVisible(true)
                setGeometry(viewState.referencePoint.cachePoint)
            }

            mapsScreenState.moveRelativeContourByTouchArea(viewState.contour)
        }
    }
}

@Composable
private fun EditContourScreen(
    mapsScreenState: MapScreenMutableState,
    viewState: MapEditContourViewState,
    dispatch: (MapsEvent) -> Unit,
) = Box(modifier = Modifier.fillMaxSize()) {

    if (!viewState.isMapDraggable) {
        TouchInterceptorOverlay(
            onTouchEvent = touch@{ touchEvent ->
                val position = touchEvent.changes.first().position
                val worldPoint = mapsScreenState.mapState.screenToWorld(
                    x = position.x,
                    y = position.y,
                ) ?: return@touch
                val event = when (touchEvent.type) {
                    PointerEventType.Press -> BeginEditContourPart(worldPoint)
                    PointerEventType.Release -> EndEditContourPart
                    PointerEventType.Move -> AddPoint(worldPoint)
                    else -> return@touch
                }
                dispatch(event)
            }
        )
    }

    Column(Modifier.align(Alignment.BottomEnd)) {
        if (viewState.showRevertButton) {
            MapControlButton(
                icon = Icons.Default.Refresh,
                onClick = { dispatch(RevertLastContourPart) },
            )
        }

        if (viewState.isMapDraggable) {
            MapControlButton(
                icon = vectorResource(Res.drawable.map_control_button_draw),
                onClick = { dispatch(SwitchToDrawContour) },
            )
        } else {
            MapControlButton(
                icon = vectorResource(Res.drawable.map_control_button_drag),
                onClick = { dispatch(SwitchToDragMap) },
            )
        }

        MapControlButton(
            icon = Icons.Default.Done,
            onClick = {
                val center = Coordinates(mapsScreenState.mapState.map!!.cameraPosition().point)
                dispatch(GoToActualFun(center, viewState.contour.points))
            },
        )
    }

    SideEffect {
        with(mapsScreenState) {
            touchAreaPlacemark().setVisible(false)

            drawEditableContour(
                viewState.contour,
                contourPlacemarks,
                savedCollection(),
                dotImage,
            )
        }
    }
}

@Composable
private fun CloseButton(modifier: Modifier = Modifier) {
    val rootController = LocalRootController.current
    MapControlButton(
        icon = Icons.Default.Close,
        onClick = {
            rootController.popBackStack()
        },
        modifier = modifier,
    )
}

@Composable
private fun TouchInterceptorOverlay(onTouchEvent: (PointerEvent) -> Unit) = Box(
    modifier = Modifier.fillMaxWidth().fillMaxHeight()
        .pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent()
                    onTouchEvent(event)
                }
            }
        }
) {

}

@Composable
private fun MapControlButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) = OutlinedButton(
    shape = CircleShape,
    onClick = onClick,
    modifier = modifier
        .padding(bottom = 24.dp)
        .padding(horizontal = 16.dp)
        .size(64.dp),
) {
    Icon(
        icon,
        contentDescription = null,
        tint = Color(0xFF196DFF),
    )
}

private fun moveRelativeContour(
    newCenter: GeoPoint,
    contour: RelativeContour,
    contourPlacemarks: MutableList<GeoPlacemark>,
    parent: GeoMapObjectCollection,
    dotImage: GeoPlacemarkImage,
) {
    ensurePlacemarksCountAtLeast(
        count = contour.positions.size,
        placemarks = contourPlacemarks,
        parent = parent,
        image = dotImage,
    )
    ensurePlacemarksVisibility(
        usedCount = contour.positions.size,
        placemarks = contourPlacemarks,
    )
    movePlacemarksUnsafe(
        contour.positions.asSequence().map { relativePosition ->
            DirectProblemSolver.solveDirectProblem(
                newCenter,
                relativePosition.courseRadians,
                relativePosition.distanceMeters,
            )
        }.asIterable(),
        contourPlacemarks,
    )
}

private fun drawEditableContour(
    contour: EditableContour,
    contourPlacemarks: MutableList<GeoPlacemark>,
    parent: GeoMapObjectCollection,
    dotImage: GeoPlacemarkImage,
) {
    ensurePlacemarksCountAtLeast(
        count = contour.points.size,
        placemarks = contourPlacemarks,
        parent = parent,
        image = dotImage,
    )

    ensurePlacemarksVisibility(
        usedCount = contour.points.size,
        placemarks = contourPlacemarks,
    )

    movePlacemarksUnsafe(
        points = contour.points,
        placemarks = contourPlacemarks,
    )
}

private fun ensurePlacemarksCountAtLeast(
    count: Int,
    placemarks: MutableList<GeoPlacemark>,
    parent: GeoMapObjectCollection,
    image: GeoPlacemarkImage,
) {
    if (placemarks.size < count) {
        for (i in (0 until count - placemarks.size)) {
            placemarks += parent.addPlacemark().also { placemark ->
                placemark.setIcon(image)
            }
        }
    }
}

private fun ensurePlacemarksVisibility(
    usedCount: Int,
    placemarks: List<GeoPlacemark>,
) {
    placemarks.take(usedCount).forEach { placemark ->
        placemark.setVisible(true)
    }
    if (placemarks.size > usedCount) {
        placemarks.takeLast(placemarks.size - usedCount).forEach { placemark ->
            placemark.setVisible(false)
        }
    }
}

private fun movePlacemarksUnsafe(
    points: Iterable<Coordinates>,
    placemarks: List<GeoPlacemark>,
) {
    points.forEachIndexed { index, point ->
        placemarks[index].setGeometry(point.cachePoint)
    }
}