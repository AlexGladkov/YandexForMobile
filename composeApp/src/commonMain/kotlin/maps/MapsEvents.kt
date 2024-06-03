package maps

interface MapsEvent

class AddPoint(val point: Coordinates): MapsEvent

class UpdateReferencePoint(val point: Coordinates): MapsEvent

class GoToActualFun(val currentMapCenter: Coordinates, val contourPoints: List<Coordinates>): MapsEvent

class GoToExpectFun: MapsEvent