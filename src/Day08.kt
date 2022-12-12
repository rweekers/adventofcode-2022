fun main() {

    fun part1(trees: List<List<Int>>): Int {
        return trees.indices
            .flatMap { y ->
                trees.first().indices
                    .map { x -> trees.isVisible(x, y) }
            }.count { it }
    }

    fun part2(trees: List<List<Int>>): Int {
        return trees.indices
            .flatMap { y ->
                trees.first().indices
                    .map { x ->
                        trees.visibleTreesFrom(x, y)
                            .map { it.takeUntil { height -> height < trees[y][x] } }
                            .map { it.size }
                            .reduce { acc, i -> acc.times(i) }
                    }
            }.max()
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    val testTrees: List<List<Int>> =
        testInput.indices.map { y -> testInput.first().indices.map { x -> testInput[y][x].digitToInt() } }

    check(part1(testTrees) == 21)
    check(part2(testTrees) == 8)

    val input = readInput("Day08")
    val trees: List<List<Int>> =
        input.indices.map { y -> input.first().indices.map { x -> input[y][x].digitToInt() } }
    println(part1(trees))
    println(part2(trees))
}

private fun List<List<Int>>.visibleTreesFrom(x: Int, y: Int): List<List<Int>> {
    val height = this.size
    val width = this.first().size
    return listOf(
        (y - 1 downTo 0).map { this[it][x] },
        (x + 1 until width).map { this[y][it] },
        (y + 1 until height).map { this[it][x] },
        (x - 1 downTo 0).map { this[y][it] }
    )
}

private fun List<List<Int>>.isVisible(x: Int, y: Int): Boolean {
    return this.visibleTreesFrom(x, y).any { treeRow ->
        treeRow.all { it < this[y][x] }
    }
}