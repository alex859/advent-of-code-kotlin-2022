import kotlin.math.absoluteValue

fun main() {
    val testInput = readInput("Day14_test.txt")
//    check(testInput.day14Part1() == 24)
    val day14Part1 = testInput.day14Part1()
//    check(day14Part1 == 93)

    val input = readInput("Day14.txt")
    println(input.day14Part1())
//    println(input.day13Part2())
}

internal fun String.day14Part1(): Int {
    val rocks = lines().flatMap { it.readRocks() }
    val result = WaterFall(rocks = rocks, sand = emptyList()).play()
    
    return result.sand.size
}

internal fun String.readRocks(): List<Tile> {
    val split = split(" -> ")
    val chunked = split.flatMapIndexed { i, el ->
        if (i == 0 || i == split.size - 1) {
            listOf(el)
        } else {
            listOf(el, el)
        }
    }.chunked(2)
    return chunked.flatMap { (startStr, endStr) ->
        val p1 = startStr.toTile()
        val p2 = endStr.toTile()
        val others = if (p1.first == p2.first) {
            if (p2.second > p1.second) verticalDown(p1, p2) else verticalUp(p1, p2)
        } else if (p1.second == p2.second){
            if (p1.first > p2.first) horizontalLeft(p1, p2) else horizontalRight(p1, p2)
        } else TODO()
        listOf(p1, p2) + others
    }.toSet().toList()

}

private fun horizontalLeft(
    p1: Pair<Int, Int>,
    p2: Pair<Int, Int>
) = (1 until (p1.first - p2.first).absoluteValue).map { p1.first - it to p1.second }

private fun horizontalRight(
    p1: Pair<Int, Int>,
    p2: Pair<Int, Int>
) = (1 until (p1.first - p2.first).absoluteValue).map { p1.first + it to p1.second }

private fun verticalDown(
    p1: Pair<Int, Int>,
    p2: Pair<Int, Int>
) = (1 until (p1.second - p2.second).absoluteValue).map { p1.first to p1.second + it }

private fun verticalUp(
    p1: Pair<Int, Int>,
    p2: Pair<Int, Int>
) = (1 until (p1.second - p2.second).absoluteValue).map { p1.first to p1.second - it }

private fun String.toTile(): Pair<Int, Int> {
    val (start, end) = split(",").map { it.toInt() }
    return start to end
}

internal fun WaterFall.play(): WaterFall {
    
    var start = this
    var next = addSand().wait()
    while(start != next) {
        start = next
        next = start.addSand().wait()
        next.print()
        println()
        println()
        println()
        println()
        println()
        println()
    }
    return next
}

internal fun WaterFall.print() {
    val minX = rocks.minOf { it.first }
    val maxX = rocks.maxOf { it.first }
    val minY = rocks.minOf { it.second }
    val maxY = rocks.maxOf { it.second }
    for (y in minY..maxY) {
        print(y)
        for (x in minX..maxX) {
            when {
                sand.contains(x to y) -> print("o")
                rocks.contains(x to y) -> print("#")
                else -> print(".")
            }
        }
        println()
    }
}

internal fun WaterFall.wait(): WaterFall {
    val next = tick()
    return if (next == this) {
        next
    } else {
        next.wait()
    }
}

internal fun WaterFall.tick(): WaterFall {
    if (fallingSand == null || (500 to 1) in sand) {
        return this
    }
    var next = fallingSand.down()
    if (isBusy(next)) {
        next = fallingSand.downLeft()
        if (isBusy(next)) {
            next = fallingSand.downRight()
        }
    }
    return when {
        isBusy(next) -> this.copy(sand = listOf(fallingSand) + sand, fallingSand = null)
        next.second > max + 2 -> this.copy(fallingSand = null)
        else -> this.copy(fallingSand = next)
    }
}

private val WaterFall.max: Int get() = rocks.maxOf { it.second }

private fun Tile.downLeft() = copy(first = first - 1, second = second + 1)

private fun Tile.downRight() = copy(first = first + 1, second = second + 1)

private fun Tile.down() = copy(second = second + 1)

private fun WaterFall.isBusy(next: Tile) = sand.contains(next) || rocks.contains(next) || next.second == max + 2

typealias Tile = Pair<Int, Int>

data class WaterFall(
    val rocks: List<Tile>,
    val sand: List<Tile>,
    val fallingSand: Tile? = null
)

internal fun WaterFall.addSand() = copy(fallingSand = 500 to 0)
