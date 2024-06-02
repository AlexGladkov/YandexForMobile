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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import di.Inject
import maps.bindings.GeoCameraPosition
import maps.bindings.GeoMapCameraListener
import maps.bindings.GeoPlacemarkImage
import maps.bindings.GeoUtils
import maps.bindings.withPoint
import maps.bindings.createMapkitPoint
import maps.bindings.point
import maps.data.Australia
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
expect fun MapsConfig.dotConfig(): GeoPlacemarkImage

@Composable
private fun MapLayout() {
    val mapState by remember { mutableStateOf(MapState()) }

    Box {

        Map(mapState) {
            if (it.finished) {
                println("wtf camera move $it")
            }
        }

        val config by derivedStateOf { Inject.di.instance<PlatformConfiguration>().mapsConfig() }
        val image = config.dotConfig()
        var savedCameraListener by remember { mutableStateOf<GeoMapCameraListener?>(null) }

        LaunchedEffect(Unit) {
            mapState.map?.let { map ->
                val initialCenter = createMapkitPoint(
                    Australia.center.lat,
                    Australia.center.lon,
                )
                val relatives = GeoUtils.calculateRelativeContour(
                    Australia.center,
                    Australia.coordinates,
                )
                val placemarksCollection = map.addCollection()

                val placemarks = Australia.coordinates.map { position ->
                    placemarksCollection.addPlacemark().apply {
                        setGeometry(createMapkitPoint(position.lat, position.lon))
                        setIcon(image)
                    }
                }

                map.moveCamera(map.cameraPosition().withPoint(point = initialCenter))

                val listener = object : GeoMapCameraListener {
                    override fun onCameraPositionChanged(
                        position: GeoCameraPosition,
                        finished: Boolean
                    ) {
                        val newCenter = position.point
                        relatives.positions.forEachIndexed { index, relativePosition ->
                            val newCoordinate = DirectProblemSolver.solveDirectProblem(
                                newCenter,
                                relativePosition.courseRadians,
                                relativePosition.distanceMeters,
                            )
                            placemarks[index].setGeometry(
                                createMapkitPoint(
                                    newCoordinate.lat,
                                    newCoordinate.lon,
                                )
                            )
                        }
                    }
                }

                savedCameraListener = listener
                map.addCameraListener(listener)

            } ?: config.log("wtf map not found")
        }
        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
//                .pointerInput(Unit) {
//                    awaitPointerEventScope {
//                        while (true) {
//                            val event = awaitPointerEvent()
//                            // handle pointer event
//                            val position = event.changes.first().position
//                            val world = mapState.screenToWorld(ScreenPoint(position.x, position.y))
//                            println("wtf $world")
//                        }
//                    }
//                }
        ) {

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