@file:OptIn(ExperimentalResourceApi::class)

package maps

import MapsConfig
import PlatformConfiguration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEvent
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
import maps.udf.EditableContour
import maps.udf.GoToActualFun
import maps.udf.GoToExpectFun
import maps.udf.MapScreen
import maps.udf.RelativeContour
import maps.utils.DirectProblemSolver
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.kodein.di.instance
import ru.alexgladkov.odyssey.compose.local.LocalRootController


@Composable
fun MapsScreen() {
    MapFunScreen()
}

@Composable
expect fun MapsConfig.dotImage(): GeoPlacemarkImage

@Composable
expect fun MapsConfig.touchAreaImage(): GeoPlacemarkImage

@Composable
private fun MapFunScreen() {
    val mapState by remember { mutableStateOf(MapState()) }

    StoredViewModel(factory = { MapsViewModel() }) { viewModel ->
        Box {
            val viewState by viewModel.viewStates().collectAsState()
            var savedCollection by remember { mutableStateOf<GeoMapObjectCollection?>(null) }
            var savedTouchAreaPlacemark by remember { mutableStateOf<GeoPlacemark?>(null) }
            // We need to hold strong reference to listener because of WeakRefs in implementations
            var touchAreaDragListener by remember {
                mutableStateOf<GeoMapObjectDragListener?>(null)
            }
            val contourPlacemarks by remember { mutableStateOf(mutableListOf<GeoPlacemark>()) }
            val config by derivedStateOf {
                Inject.di.instance<PlatformConfiguration>().mapsConfig()
            }
            val dotImage = config.dotImage()
            val areaImage = config.touchAreaImage()

            fun savedCollection(): GeoMapObjectCollection {
                return savedCollection
                    ?: (mapState.map!!.addCollection().also { savedCollection = it })
            }

            fun touchAreaPlacemark(): GeoPlacemark {
                return savedTouchAreaPlacemark
                    ?: savedCollection().addPlacemark().also { savedTouchAreaPlacemark = it }
            }

            fun moveRelativeContour() {
                val screen = viewState.screen as? MapScreen.ActualFun ?: return

                moveRelativeContour(
                    touchAreaPlacemark().getGeometry(),
                    screen.contour,
                    contourPlacemarks,
                    savedCollection(),
                    dotImage,
                )
            }

            Map(
                state = mapState,
                onCameraMoved = { moveRelativeContour() },
            )

            LaunchedEffect(Unit) {
                val map = mapState.map ?: return@LaunchedEffect
                touchAreaPlacemark().let { touchArea ->
                    val listener = DragsPositionsListener { moveRelativeContour() }
                    touchAreaDragListener = listener
                    with(touchArea) {
                        setDragListener(listener)
                        setDraggable(true)
                        setGeometry(map.cameraPosition().point)
                        setIcon(areaImage)
                    }
                }
            }

            SideEffect {
                mapState.map ?: return@SideEffect

                when (val screen = viewState.screen) {
                    is MapScreen.ExpectFun -> {
                        touchAreaPlacemark().setVisible(false)

                        drawEditableContour(
                            screen.contour,
                            contourPlacemarks,
                            savedCollection(),
                            dotImage,
                        )
                    }

                    is MapScreen.ActualFun -> {
                        touchAreaPlacemark().run {
                            setVisible(true)
                            setGeometry(screen.referencePoint.cachePoint)
                        }

                        moveRelativeContour()
                    }
                }

            }

            if (!viewState.isMapDraggable) {
                TouchInterceptorOverlay { event ->
                    val position = event.changes.first().position

                    mapState.screenToWorld(x = position.x, y = position.y)?.let {
                        viewModel.obtainEvent(AddPoint(it))
                    }
                }
            }

            CloseButton(Modifier.align(Alignment.BottomStart))

            MapControlButton(
                if (viewState.isMapDraggable) {
                    Icons.Default.Edit
                } else {
                    Icons.Default.Done
                },
                onClick = {
                    val event = when (val screen = viewState.screen) {
                        is MapScreen.ActualFun -> {
                            GoToExpectFun()
                        }

                        is MapScreen.ExpectFun -> {
                            val center = Coordinates(mapState.map!!.cameraPosition().point)
                            GoToActualFun(center, screen.contour.points)
                        }
                    }
                    viewModel.obtainEvent(event)
                },
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}

@Composable
fun CloseButton(modifier: Modifier = Modifier) {
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
fun TouchInterceptorOverlay(onTouchEvent: (PointerEvent) -> Unit) = Box(
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
fun MapControlButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) = OutlinedButton(
    shape = CircleShape,
    onClick = onClick,
    modifier = modifier
        .padding(horizontal = 16.dp, vertical = 24.dp)
        .size(64.dp),
) {
    Icon(
        icon,
        contentDescription = null,
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