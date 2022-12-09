import Movement.*
import kotlin.math.absoluteValue

fun main() {
    val testInput = readInput("Day09_test.txt")
    check(testInput.readMovements().tailPositions().size == 13)
//    check(testInput.result2() == 24933642)

    val input = readInput("Day09.txt")
    println(input.readMovements().tailPositions().size)
//    println(input.result2())
}

internal fun List<Movement>.tailPositions() = fold(
    emptySet<Position>() to Rope(head = Position(5, 5), tail = Position(5, 5))
) { (positions, rope), movement ->
    val next = rope.moveHead(movement)
    (positions + next.tail) to next
}.first

internal fun String.readMovements(): List<Movement> {
    val result = mutableListOf<Movement>()
    lines().forEach {
        val (type, steps) = it.split(" ")
        repeat(steps.toInt()) {
            result.add(
                when (type) {
                    "R" -> Right
                    "U" -> Up
                    "D" -> Down
                    "L" -> Left
                    else -> error("unknown $type")
                }
            )
        }
    }
    return result
}

fun Rope.moveHead(move: Movement): Rope {
    val newHead = head.move(move)
    val leftDistance = (newHead.left - tail.left)
    val bottomDistance = (newHead.bottom - tail.bottom)

    val newTail =
        if (head.left == tail.left || head.bottom == tail.bottom) {
            if (leftDistance.absoluteValue > 1 || bottomDistance.absoluteValue > 1) {
                head.copy()
            } else tail
        } else {
            if (head.bottom > tail.bottom) {
                if (head.left > tail.left) {
                    when (move) {
                        Up, Right -> head.copy()
                        Down, Left -> tail
                    }
                } else {
                    when (move) {
                        Up, Left -> head.copy()
                        Down, Right -> tail
                    }
                }
            } else {
                if (head.left > tail.left) {
                    when (move) {
                        Down, Right -> head.copy()
                        Up, Left -> tail
                    }
                } else {
                    when (move) {
                        Down, Left -> head.copy()
                        Up, Right -> tail
                    }
                }
            }
        }

    return copy(head = newHead, tail = newTail)
}


private fun Position.move(move: Movement) = when (move) {
    Right -> copy(left = left + 1)
    Left -> copy(left = left - 1)
    Up -> copy(bottom = bottom + 1)
    Down -> copy(bottom = bottom - 1)
}

enum class Movement {
    Right, Left, Up, Down
}

data class Position(
    val left: Int,
    val bottom: Int
)

data class Rope(
    val head: Position,
    val tail: Position
)