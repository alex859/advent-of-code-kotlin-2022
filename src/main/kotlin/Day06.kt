fun main() {
    val testInput = readInput("Day06_test.txt")
    check(testInput.getStartOfPacket(markerSize = 4) == 10)
    check(testInput.getStartOfPacket(markerSize = 14) == 29)

    val input = readInput("Day06.txt")
    println(input.getStartOfPacket(markerSize = 4))
    println(input.getStartOfPacket(markerSize = 14))
}

internal fun String.getStartOfPacket(markerSize: Int, start: Int = 0): Int? {
    if (length < markerSize) {
        return null
    }

    return if (substring(0, markerSize).hasNoDuplicates()) {
        markerSize + start
    } else {
        drop(1).getStartOfPacket(markerSize, start = start + 1)
    }
}

private fun String.hasNoDuplicates() = toSet().size == length