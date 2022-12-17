import java.util.PriorityQueue

class Grid<T>(private val items: Map<Point, T>) {

    private data class PathCost(val point: Point, val cost: Int) : Comparable<PathCost> {
        override fun compareTo(other: PathCost): Int =
            this.cost.compareTo(other.cost)
    }

    fun shortestPathBFS(
        begin: Point,
        end: Point,
        goalReached: (Point) -> Boolean,
        moveAllowed: (T, T) -> Boolean
    ): Int {
        val seen = mutableSetOf<Point>()
        val queue = PriorityQueue<PathCost>().apply { add(PathCost(begin, 0)) }

        while (queue.isNotEmpty()) {
            val nextPoint = queue.poll()

            if (nextPoint.point !in seen) {
                seen += nextPoint.point
                val neighbors = nextPoint.point.cardinalNeighbors()
                    .filter { it in items }
                    .filter { moveAllowed(items.getValue(nextPoint.point), items.getValue(it)) }
                if (neighbors.any { goalReached(it) }) return nextPoint.cost + 1
                queue.addAll(neighbors.map { PathCost(it, nextPoint.cost + 1) })
            }
        }
        throw IllegalStateException("No valid path from $begin to $end")
    }
}