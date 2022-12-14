fun main() {
    val testInput = readInput("Day13_test.txt")
    check(testInput.day13Part1() == 13)
    check(testInput.day13Part2() == 140)

    val input = readInput("Day13.txt")
    println(input.day13Part1())
    println(input.day13Part2())
}


internal fun String.day13Part1() =
    split("\n\n")
        .mapIndexed { i, packets ->
            (i + 1) to packets
        }
        .filter { (_, packet) ->
            val (leftPacket, rightPacket) = packet.split("\n")
            leftPacket.toPacketList() < rightPacket.toPacketList()
        }.sumOf { (index, _) -> index }

internal fun String.day13Part2(): Int {
    val divider1 = "[[2]]".toPacketList()
    val divider2 = "[[6]]".toPacketList()
    val all = 
        replace("\n\n", "\n").lines().map { it.toPacketList() } + listOf(divider1) + listOf(divider2)
    val sorted = all.sortedWith { left, right -> (left as List<*>).compareTo(right as List<*> ) }
    val filter = sorted.mapIndexed { index, packet -> index + 1 to packet }
        .filter { (index, packet) -> packet == divider1 || packet == divider2 }
    return filter
        .map { (index, _) -> index }
        .reduce { a, b -> a * b }

}

internal operator fun List<*>.compareTo(that: List<*>): Int {
    return if (isEmpty()) {
        if (that.isEmpty()) 0
        else -1
    } else {
        if (that.isEmpty()) 1
        else {
            val left = this.first()
            val right = that.first()
            when (isSmaller(left, right)) {
                1 -> 1
                -1 -> -1
                else -> this.drop(1).compareTo(that.drop(1))
            }
        }
    }
}

private fun isSmaller(left: Any?, right: Any?) = when (left) {
    is Int -> when (right) {
        is Int -> left.compareTo(right)
        is List<*> -> listOf(left).compareTo(right)
        else -> TODO()
    }

    is List<*> -> when (right) {
        is List<*> -> left.compareTo(right)
        is Int -> left.compareTo(listOf(right))
        else -> TODO()
    }
    else -> TODO()
}

internal fun String.toPacketList(): List<Any> {
    val stack = mutableListOf<MutableList<Any>>()
    val stackN = mutableListOf<Char>()
    for (c in this) {
        if (c == '[') {
            stack.add(0, mutableListOf())
        }
        if (c.isDigit()) {
            stackN.add(c)
        }
        if (c == ',') {
            if (stackN.isNotEmpty()) {
                stack.first().add(stackN.joinToString(separator = "").toInt())
                stackN.clear()
            }
        }
        if (c == ']') {
            if (stackN.isNotEmpty()) {
                stack.first().add(stackN.joinToString(separator = "").toInt())
                stackN.clear()
            }
            val current = stack.removeFirst()
            if (stack.isEmpty()) stack.add(current)
            else stack.first().add(current)
        }
    }
    return stack.first()
}