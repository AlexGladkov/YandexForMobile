@file:OptIn(ExperimentalResourceApi::class)

package maps

import MapsConfig
import PlatformConfiguration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import di.Inject
import io.github.alexgladkov.kviewmodel.odyssey.StoredViewModel
import maps.bindings.GeoMapObject
import maps.bindings.GeoMapObjectCollection
import maps.bindings.GeoMapObjectDragListener
import maps.bindings.GeoPlacemark
import maps.bindings.GeoPlacemarkImage
import maps.bindings.GeoPoint
import maps.bindings.makeGeoScreenPoint
import maps.bindings.point
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.kodein.di.instance
import worlds.composeapp.generated.resources.Res
import worlds.composeapp.generated.resources.browser_background

private val items = mapsItems()

@Composable
fun MapsScreenView() {

    MapLayout()

//    DefaultLayout()
}

@Composable
expect fun MapsConfig.dotImage(): GeoPlacemarkImage

@Composable
expect fun MapsConfig.touchAreaImage(): GeoPlacemarkImage

@Composable
private fun MapLayout() {
    val mapState by remember { mutableStateOf(MapState()) }

    StoredViewModel(factory = { MapsViewModel() }) { viewModel ->
        Box {
            val viewState by viewModel.viewStates().collectAsState()
            var savedCollection by remember { mutableStateOf<GeoMapObjectCollection?>(null) }
            var savedTouchAreaPlacemark by remember { mutableStateOf<GeoPlacemark?>(null) }
            val contourPlacemarks by remember { mutableStateOf(mutableListOf<GeoPlacemark>()) }

            fun savedCollection(): GeoMapObjectCollection {
                return savedCollection
                    ?: (mapState.map!!.addCollection().also { savedCollection = it })
            }

            fun touchAreaPlacemark(): GeoPlacemark {
                return savedTouchAreaPlacemark
                    ?: savedCollection().addPlacemark().also { savedTouchAreaPlacemark = it }
            }

            fun moveRelativeContour() {
                val screen = viewState.screen as? MapsScreen.Fun.ActualFun ?: return
                val newCenter = touchAreaPlacemark().getGeometry()
                screen.contour.positions.forEachIndexed { index, relativePosition ->
                    val newCoordinate = DirectProblemSolver.solveDirectProblem(
                        newCenter,
                        relativePosition.courseRadians,
                        relativePosition.distanceMeters,
                    )
                    contourPlacemarks[index].setGeometry(newCoordinate.cachePoint)
                }
            }

            Map(mapState) {
                moveRelativeContour()
            }

            val config by derivedStateOf {
                Inject.di.instance<PlatformConfiguration>().mapsConfig()
            }
            val image = config.dotImage()
            val areaImage = config.touchAreaImage()

            var savedDragListener by remember { mutableStateOf<GeoMapObjectDragListener?>(null) }

            LaunchedEffect(Unit) {
                val map = mapState.map ?: return@LaunchedEffect
                touchAreaPlacemark().let { touchArea ->
                    val listener = object : GeoMapObjectDragListener {
                        override fun onMapObjectDragStart(mapObject: GeoMapObject) {
                        }

                        override fun onMapObjectDrag(mapObject: GeoMapObject, point: GeoPoint) {
                            moveRelativeContour()
                        }

                        override fun onMapObjectDragEnd(mapObject: GeoMapObject) {
                        }

                    }
                    savedDragListener = listener
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
                    is MapsScreen.Fun.ExpectFun -> {
                        touchAreaPlacemark().setVisible(false)
                        val collection = savedCollection()
                        if (contourPlacemarks.size < screen.contour.points.size) {
                            for (i in (0 until screen.contour.points.size - contourPlacemarks.size)) {
                                contourPlacemarks += collection.addPlacemark().also { placemark ->
                                    placemark.setIcon(image)
                                }
                            }
                        }
                        if (contourPlacemarks.size > screen.contour.points.size) {
                            contourPlacemarks.takeLast(contourPlacemarks.size - screen.contour.points.size)
                                .forEach { placemark ->
                                    placemark.setVisible(false)
                                }
                        }

                        screen.contour.points.forEachIndexed { index, point ->
                            contourPlacemarks[index].apply {
                                setGeometry(point.cachePoint)
                                setVisible(true)
                            }
                        }
                    }

                    is MapsScreen.Fun.ActualFun -> {
                        touchAreaPlacemark().run {
                            setVisible(true)
                            setGeometry(screen.referencePoint.cachePoint)
                        }
                    }

                    else -> {}
                }

            }

            if (!viewState.isMapDraggable) {
                Box(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight()
                        .pointerInput(Unit) {
                            awaitPointerEventScope {
                                while (true) {
                                    val event = awaitPointerEvent()
                                    // handle pointer event
                                    val position = event.changes.first().position

                                    mapState.screenToWorld(
                                        makeGeoScreenPoint(position.x, position.y)
                                    )
                                        ?.let(::AddPoint)
                                        ?.let(viewModel::obtainEvent)
                                }
                            }
                        }
                ) {

                }
            }

            OutlinedButton(
                shape = CircleShape,
                onClick = {
                    val event = when (val screen = viewState.screen) {
                        MapsScreen.Boring -> null
                        is MapsScreen.Fun.ActualFun -> {
                            GoToExpectFun()
                        }

                        is MapsScreen.Fun.ExpectFun -> {
                            val center = Coordinates(mapState.map!!.cameraPosition().point)
                            GoToActualFun(center, screen.contour.points)
                        }
                    }
                    event?.let(viewModel::obtainEvent)
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .size(64.dp)
                    .align(Alignment.BottomEnd),
            ) {

                val icon = if (viewState.isMapDraggable) {
                    Icons.Default.Edit
                } else {
                    Icons.Default.Done
                }
                Icon(
                    icon,
                    contentDescription = null,
                    modifier = Modifier,
                )
            }

        }
    }
}

@Composable
private fun DefaultLayout() {
    Box(
        modifier = Modifier
            .background(Color(0x88EEEEEE))

    ) {
        Image(
            painter = painterResource(Res.drawable.browser_background),
            modifier = Modifier.fillMaxSize(),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.padding(16.dp).windowInsetsPadding(WindowInsets.systemBars)) {
            Text(text = "Яндекс Карты", fontSize = 28.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Яндекс Карты – одно из крупнейших приложений Яндекса, " +
                        "гео-суперапп, позволяющий пользователям использовать сценарии навигации " +
                        "на различных видах транспорта, искать места(организации, достопримечательности). ",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(20.dp))

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 16.dp,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                content = {

                    items(items.size) { index ->
                        AppItem(items[index])
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


@Composable
private fun AppItem(item: MapsItem) {

    Box(
        modifier = Modifier
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .height(200.dp)
            .background(item.backgroundColor),
        contentAlignment = Alignment.TopStart
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Image(
                painter = painterResource(item.icon),
                modifier = Modifier.size(item.iconSize.dp),
                contentDescription = "Иконка для ${item.title}",
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = item.title,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 10.dp, top = 12.dp)
        )
    }
}