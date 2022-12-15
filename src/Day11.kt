fun main() {

    fun part1(monkeys: List<Monkey>, rounds: Int): Long {
        (1..rounds).forEach { _ ->
            monkeys.forEach { monkey -> monkey.evaluate(monkeys) { it.floorDiv(3) } }
        }
        return monkeys
            .sortedByDescending { it.inspections }
            .take(2)
            .fold(1L) { acc, curr -> acc * curr.inspections }
    }

    fun part2(monkeys: List<Monkey>, rounds: Int): Long {
        val testProduct: Long = monkeys.map { it.test }.reduce { acc, curr -> acc * curr}
        (1..rounds).forEach { _ ->
            monkeys.forEach { monkey -> monkey.evaluate(monkeys) { it % testProduct } }
        }
        return monkeys
            .sortedByDescending { it.inspections }
            .take(2)
            .fold(1L) { acc, curr -> acc * curr.inspections }
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    val testMonkeysSilver = determineMonkeys(testInput)
    val testMonkeysGold = determineMonkeys(testInput)
    check(part1(testMonkeysSilver, 20) == 10605L)
    check(part2(testMonkeysGold, 10000) == 2713310158L)

    val input = readInput("Day11")
    val monkeysSilver = determineMonkeys(input)
    val monkeysGold = determineMonkeys(input)
    println(part1(monkeysSilver, 20))
    println(part2(monkeysGold, 10000))
}

private fun determineMonkeys(input: List<String>): List<Monkey> =
    input
        .splitBy { it.isEmpty() }
        .map {
            val items = it[1].substringAfter("Starting items: ", "").trim().split(", ").map { it.toLong() }

            val operand = it[2].substringAfterLast(" ")
            val operator = it[2].substringAfter("old ").first().toString()
            val operation: (Long) -> Long = { v ->
                if (operator == "*") {
                    if (operand == "old") {
                        v * v
                    } else {
                        v * operand.toLong()
                    }
                } else if (operator == "+") {
                    if (operand == "old") {
                        v + v
                    } else {
                        v + operand.toLong()
                    }
                } else {
                    throw IllegalArgumentException("Unknown operand $operator")
                }
            }
            val test = it[3].substringAfterLast(" ").toLong()
            val monkeyTrue = it[4].substringAfterLast(" ").toInt()
            val monkeyFalse = it[5].substringAfterLast(" ").toInt()

            Monkey(items.toMutableList(), operation, test, monkeyTrue, monkeyFalse)
        }

private class Monkey(
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    val test: Long,
    val monkeyTrue: Int,
    val monkeyFalse: Int,
    var inspections: Long = 0L
) {
    fun evaluate(monkeys: List<Monkey>, extraOperation: (Long) -> Long = { it }) {
        items.forEach { item ->
            val worryLevel = extraOperation(operation(item))
            if (worryLevel % test == 0L) {
                monkeys[monkeyTrue].items.add(worryLevel)
            } else {
                monkeys[monkeyFalse].items.add(worryLevel)
            }
        }
        inspections += items.size
        items.clear()
    }
}
