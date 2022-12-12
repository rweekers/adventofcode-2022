import java.util.function.Predicate

fun <T> List<T>.splitBy(predicate: (T) -> Boolean): List<List<T>> {
    if (isEmpty()) return listOf(this)
    val list = mutableListOf<List<T>>()
    var index = 0
    this.forEachIndexed { i, v ->
        if (predicate(v)) {
            list.add(this.subList(index, i))
            index = i + 1
        } else if (this.subList(index, this.size).none { predicate(it) }) {
            list.add(this.subList(index, i + 1))
        }
    }
    return list
}

fun String.commonCharacter(other: String): Char =
    this.reduce { acc, c ->
        if (other.contains(c)) {
            c
        } else {
            acc
        }
    }

fun String.commonCharacters(other: String): String =
    this.fold("") { acc, c ->
        if (other.contains(c)) {
            acc + c
        } else {
            acc
        }
    }

fun String.allDifferentCharacters(): Boolean {
    return this.filterIndexed { index, c -> this.removeRange(index, index + 1).any { it == c } }
        .firstOrNull() == null
}

fun List<Int>.takeUntil(predicate: Predicate<Int>): List<Int> {
    val filtered = mutableListOf<Int>()
    this.asSequence()
        .forEach {
            filtered.add(it)
            if (!predicate.test(it)) return filtered
        }
    return filtered
}

typealias Stack<T> = ArrayDeque<T>

fun <T> Stack<T>.push(element: T) = addLast(element)

fun <T> Stack<T>.pop() = removeLastOrNull()

fun <T> Stack<T>.push(elements: List<T>) = addAll(elements)

fun <T> Stack<T>.pop(elements: Int): List<T> {
    val e = takeLast(elements)
    repeat((1..elements).count()) { this.removeLast() }
    return e
}