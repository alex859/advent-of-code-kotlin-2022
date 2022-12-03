import Choice.*
import FixedResult.*

fun main() {
    val testInput = readInput("Day02_test.txt")
    check(testInput.total { it.toRound().myScore } == 15)
    check(testInput.total { it.toRoundFixing().toRound().myScore } == 12)

    val input = readInput("Day02.txt")
    println(input.total { it.toRound().myScore })
    println(input.total { it.toRoundFixing().toRound().myScore })
}

internal fun String.toRound(): Round {
    val (opponentChoiceStr, myChoiceStr) = split(" ")
    return Round(
        myChoice = myChoiceStr.toChoice(myChoices),
        opponentChoice = opponentChoiceStr.toChoice(opponentChoices)
    )
}

internal fun String.toRoundFixing(): RoundFixing {
    val (opponentChoiceStr, fixedResultStr) = split(" ")
    return opponentChoiceStr.toChoice(opponentChoices) to fixedResultStr.toFixingResult()
}

internal data class Round(
    val myChoice: Choice,
    val opponentChoice: Choice
)

internal enum class Choice {
    ROCK, SCISSORS, PAPER
}

internal enum class FixedResult {
    WIN, LOSE, DRAW
}

internal typealias RoundFixing = Pair<Choice, FixedResult>

internal fun RoundFixing.toRound(): Round {
    val (opponentChoice, fixedResult) = this
    val myChoice = when (fixedResult) {
        DRAW -> opponentChoice
        WIN -> results.values.first { it.loser == opponentChoice }.winner
        LOSE -> results.values.first { it.winner == opponentChoice }.loser
    }
    return Round(myChoice = myChoice, opponentChoice = opponentChoice)
}

private sealed interface Result {
    object Draw : Result
    data class NonDraw(
        val winner: Choice,
        val loser: Choice
    ) : Result
}

private val Round.result: Result
    get() {
        val (choice1, choice2) = this
        return if (choice1 == choice2) Result.Draw
        else results[choice1 vs choice2]
            ?: results[choice2 vs choice1]
            ?: error("unable to find result for $this")
    }

private val results = mapOf(
    (ROCK vs PAPER) to Result.NonDraw(winner = PAPER, loser = ROCK),
    (ROCK vs SCISSORS) to Result.NonDraw(winner = ROCK, loser = SCISSORS),
    (SCISSORS vs PAPER) to Result.NonDraw(winner = SCISSORS, loser = PAPER),
)

private fun Result.scoreFor(choice: Choice) = when (this) {
    is Result.Draw -> 3
    is Result.NonDraw -> {
        with(this) {
            if (choice == winner) 6
            else 0
        }
    }
}

internal val Round.myScore: Int get() = myChoice.score + result.scoreFor(myChoice)

private val Choice.score: Int
    get() = when (this) {
        ROCK -> 1
        PAPER -> 2
        SCISSORS -> 3
    }

private infix fun Choice.vs(that: Choice): Round = Round(myChoice = this, opponentChoice = that)

private fun String.toFixingResult() = when (this) {
    "X" -> LOSE
    "Y" -> DRAW
    "Z" -> WIN
    else -> error("unrecognised result $this")
}

private fun String.toChoice(map: Map<String, Choice>) = map[this] ?: error("unrecognised choice code: $this")

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

internal fun String.total(f: (String) -> Int): Int = lines().sumOf { f(it) }
