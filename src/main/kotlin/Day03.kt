fun main() {
    val testInput = readInput("Day03_test.txt")
    check(testInput.itemsPriority() == 157)
    check(testInput.badgesPriority() == 70)

    val input = readInput("Day03.txt")
    println(input.itemsPriority())
    println(input.badgesPriority())
}

fun String.itemsPriority() = lines().map { it.itemInCommon() }.sumOf { it.priority }

internal fun String.itemInCommon(): Char {
    val (compartment1, compartment2) = compartments
    val intersect = compartment1.toSet().intersect(compartment2.toSet())
    return intersect.first()
}

internal val Char.priority: Int
    get() = if (this.isLowerCase()) code - 'a'.code + 1 else code - 'A'.code + 27

internal val String.compartments: Pair<String, String>
    get() {
        val half = length / 2
        return substring(0 until half) to substring(half until length)
    }

fun String.badgesPriority(): Int = lines().groups.map { it.badge }.sumOf { it.priority }

internal val List<String>.groups: Iterable<Iterable<String>>
    get() = this.chunked(3)

internal val Iterable<String>.badge: Char
    get() {
        val (first, second, third) = map { it.toSet() }
        return first.intersect(second).intersect(third).first()
    }