package maps.udf

import maps.bindings.Coordinates

interface MapsEvent

object SwitchToDrawContour: MapsEvent
object SwitchToDragMap: MapsEvent
class AddPoint(val point: Coordinates): MapsEvent
class BeginEditContourPart(val point: Coordinates): MapsEvent
object EndEditContourPart: MapsEvent
object RevertLastContourPart: MapsEvent

class UpdateReferencePoint(val point: Coordinates): MapsEvent

class GoToActualFun(val currentMapCenter: Coordinates, val contourPoints: List<Coordinates>):
    MapsEvent

class GoToExpectFun: MapsEvent