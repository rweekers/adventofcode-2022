import kotlin.math.absoluteValue
import kotlin.math.sign

data class Point(val x: Int, val y: Int) {

    fun isVerticalOrHorizontalWith(otherPoint: Point): Boolean {
        return x == otherPoint.x || y == otherPoint.y
    }

    fun isDiagonalTo(otherPoint: Point): Boolean {
        return (x - otherPoint.x).absoluteValue == (y - otherPoint.y).absoluteValue
    }

    fun touches(otherPoint: Point): Boolean =
        (this.x - otherPoint.x).absoluteValue <= 1 && (this.y - otherPoint.y).absoluteValue <= 1

    fun cardinalNeighbors(): Set<Point> =
        setOf(
            copy(x = x - 1),
            copy(x = x + 1),
            copy(y = y - 1),
            copy(y = y + 1)
        )

    fun lineTo(that: Point): List<Point> {
        val xDelta = (that.x - x).sign
        val yDelta = (that.y - y).sign

        val steps = maxOf((x - that.x).absoluteValue, (y - that.y).absoluteValue)

        return (1 .. steps).scan(this) { last, _ -> Point(last.x + xDelta, last.y + yDelta) }
    }
}