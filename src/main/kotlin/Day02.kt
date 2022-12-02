import Choice.*
import Result.*

fun main() {
    val testInput = readInput("Day02_test.txt")
    check(testInput.total { it.score() } == 15)
    check(testInput.total { it.updatedScore() } == 12)

    val input = readInput("Day02.txt")
    println(input.total { it.score() })
    println(input.total { it.updatedScore() })
}

fun String.total(f: (String) -> Int): Int = lines().sumOf { f(it) }

internal fun String.score(): Int = when (this) {
    "A X" -> 4
    "B X" -> 1
    "C X" -> 7
    "A Y" -> 8
    "B Y" -> 5
    "C Y" -> 2
    "A Z" -> 3
    "B Z" -> 9
    "C Z" -> 6
    else -> error("unrecognised input $this")
}

internal fun String.updatedScore(): Int {
    val (opponentChoice, result) = split(" ").let { (opponentCode, resultCode) ->
        opponentCode.toChoice(opponentChoices) to resultCode.toResult()
    }
    val myChoice = myChoice(opponentChoice, result)

    return "${opponentChoice.toCode(opponentChoices)} ${myChoice.toCode(myChoices)}".score()
}

private fun myChoice(opponentChoice: Choice, expectedResult: Result) =  when (expectedResult) {
    DRAW -> opponentChoice
    WIN -> winner(opponentChoice)
    LOSE -> loser(opponentChoice)
}

private fun String.toResult() = when (this) {
    "X" -> LOSE
    "Y" -> DRAW
    "Z" -> WIN
    else -> error("unrecognised result $this")
}

private fun String.toChoice(map: Map<String, Choice>) = map[this] ?: error("unrecognised choice code: $this")

private fun Choice.toCode(map: Map<String, Choice>) = map.reversed()[this] ?: error("unrecognised choice $this")

private val opponentChoices = mapOf(
        "A" to ROCK,
        "B" to PAPER,
        "C" to SCISSORS,
)

private val myChoices = mapOf(
        "X" to ROCK,
        "Y" to PAPER,
        "Z" to SCISSORS,
)

private enum class Result {
    WIN, LOSE, DRAW
}

private enum class Choice {
    ROCK, SCISSORS, PAPER
}

private val winnerMap = mapOf(
        ROCK to PAPER,
        SCISSORS to ROCK,
        PAPER to SCISSORS
)

private fun winner(choice: Choice) = winnerMap[choice] ?: error("choice not found: $choice")

private fun loser(choice: Choice) = winnerMap.reversed()[choice] ?: error("choice not found $choice")

private fun <K, V> Map<K, V>.reversed() = entries.associate { (key, value) -> value to key }