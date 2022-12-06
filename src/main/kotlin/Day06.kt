fun main() {
    val testInput = readInput("Day06_test.txt")
    check(testInput.startOfPacket(markerSize = 4) == 10)
    check(testInput.startOfPacket(markerSize = 14) == 29)

    val input = readInput("Day06.txt")
    println(input.startOfPacket(markerSize = 4))
    println(input.startOfPacket(markerSize = 14))
}

internal fun String.startOfPacket(markerSize: Int, start: Int = 0): Int? = when {
    length < markerSize -> null
    take(markerSize).hasNoDuplicates() -> markerSize + start
    else -> drop(1).startOfPacket(markerSize, start = start + 1)
}

private fun String.hasNoDuplicates() = toSet().size == length