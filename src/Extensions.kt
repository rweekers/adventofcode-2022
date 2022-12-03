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