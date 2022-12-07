fun main() {

    fun part1(input: List<String>): Long {
        return determineCumulativeDirectorySizes(input)
            .filter { it.value <= 100000 }
            .map { it.value }
            .sum()
    }

    fun part2(input: List<String>): Long {
        val cumulativeSizes = determineCumulativeDirectorySizes(input)

        val spaceUnused = 70_000_000 - (cumulativeSizes["/"] ?: throw IllegalArgumentException())
        val spaceNeeded = 30_000_000 - spaceUnused

        return cumulativeSizes
            .filter { it.value >= spaceNeeded }
            .minOf { it.value }
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")

    check(part1(testInput) == 95437L)
    check(part2(testInput) == 24933642L)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

private fun parseCliOutput(input: List<String>): List<CliOutput> =
    input
        .filterIndexed { index, s -> index != 0 }
        .filterNot { it.filterNot { c -> c.isWhitespace() }.startsWith("\$ls") }
        .map {
            if (it.filterNot { c -> c.isWhitespace() }.startsWith("\$cd")) {
                Command(it.split(" ")[2])
            } else if (it.split(" ")[0] == "dir") {
                Directory(it.split(" ")[1])
            } else {
                Output(it.split(" ")[1], it.split(" ")[0].toLong())
            }
        }

private fun determineDirectorySizes(cliOutput: List<CliOutput>): Map<String, Long> =
    buildMap {
        var currentDir = "/"
        put(currentDir, 0)
        cliOutput.forEach {
            when (it) {
                is Directory -> {
                    // do nothing
                }

                is Command -> {
                    currentDir = if (it.location == "..") currentDir.substring(
                        0,
                        currentDir.lastIndexOf("/")
                    ) else (currentDir + "/${it.location}").replace("//", "/")
                    if (currentDir.isNotEmpty()) {
                        putIfAbsent(currentDir, 0)
                    }
                }

                is Output -> this[currentDir] = (this[currentDir] ?: throw IllegalArgumentException()) + it.size
            }
        }
    }

private fun determineCumulativeDirectorySizes(input: List<String>): Map<String, Long> {
    val sizes = determineDirectorySizes(parseCliOutput(input))

    return buildMap {
        sizes
            .forEach { m ->
                val totalValue = sizes
                    .filter { it.key.startsWith(m.key) }
                    .map { it.value }
                    .sum()
                this[m.key] = totalValue
            }
    }
}

sealed interface CliOutput
class Command(val location: String) : CliOutput
class Output(val name: String, val size: Long) : CliOutput
class Directory(val name: String) : CliOutput