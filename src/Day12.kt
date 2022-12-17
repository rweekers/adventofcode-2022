fun main() {

    fun part1(input: List<String>): Int {
        val grid = determineGrid(input)

        val start: Point = input.determinePointFor("S")
        val end: Point = input.determinePointFor("E")

        return grid.shortestPathBFS(
            begin = start,
            end = end,
            goalReached = { it == end },
            moveAllowed = { from, to -> to - from <= 1 }
        )
    }

    fun part2(input: List<String>): Int {
        val grid = determineGrid(input)

        val endPoint = input.determinePointFor("E")
        val startPoints = buildList {
            add(input.determinePointFor("S"))
            addAll(input.determinePointsFor("a"))
        }

        return startPoints
            .mapNotNull { startPoint ->
                try {
                    grid.shortestPathBFS(
                        begin = startPoint,
                        end = endPoint,
                        goalReached = { it == endPoint },
                        moveAllowed = { from, to -> to - from <= 1 }
                    )
                } catch (e: IllegalStateException) {
                    null
                }
            }
            .min()
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}

private fun determineGrid(input: List<String>): Grid<Int> {
    val gridPoints = input.flatMapIndexed { indexY, row ->
        row.mapIndexed { indexX, c ->
            val current = Point(indexX, indexY)
            current to when (c) {
                'S' -> 0
                'E' -> 25
                else -> c.code - 'a'.code
            }
        }
    }.toMap()

    return Grid(gridPoints)
}
