fun main() {
    val testInput = readInput("Day11_test.txt")
    check(testInput.day11Part1() == 10605)
//    check(testInput.day10Part2() == )

    val input = readInput("Day11.txt")
    println(input.day11Part1())
//    println(input.day10Part2())
}

private fun String.day11Part1(): Int {
    val endGame = split("\n\n").map { it.toMonkey() }.playRounds()
    return endGame.sortedByDescending { it.itemsInspected }.take(2).map { it.itemsInspected }.reduce { a, b -> a * b }
}

internal fun List<Monkey>.playRounds(): List<Monkey> {
    var monkeys = this
    for (i in 1..20) {
        monkeys = monkeys.playRound()
    }
    return monkeys
}

internal fun List<Monkey>.playRound(): List<Monkey> {
    var monkeys = this
    for (i in indices) {
        monkeys = monkeys.playTurn(i)
    }
    return monkeys
}

internal fun List<Monkey>.playTurn(index: Int): List<Monkey> {
    val monkeys = this.toMutableList()
    var monkey = monkeys[index]
    while (monkey.items.isNotEmpty()) {
        val result = monkeys[index].playTurn()
        monkeys[index] = result.monkey
        monkey = result.monkey
        if (result.pass != null) {
            val otherMonkey = monkeys[result.pass.second]
            monkeys[result.pass.second] = otherMonkey.copy(items = otherMonkey.items + result.pass.first)
        }
    }


    return monkeys
}

private fun Monkey.playTurn(): TurnResult {
    val item = items.first()
    val worry = when (val op = operation) {
        is Times -> item * when (val n = op.n) {
            is N -> n.value
            is Old -> item
        }

        is Plus -> item + when (val n = op.n) {
            is N -> n.value
            is Old -> item
        }
    } / 3

    val pass = worry to if (worry % divisibleBy == 0) {
        nextMonkeyIfTrue
    } else {
        nextMonkeyIfFalse
    }
    return TurnResult(copy(items = items - item, itemsInspected = itemsInspected + 1), pass)
}

data class TurnResult(
    val monkey: Monkey,
    val pass: Pair<Int, Int>?
)

internal fun String.toMonkey(): Monkey {
    val lines = lines()
    return Monkey(
        code = lines[0].toCode(),
        items = lines[1].toItems(),
        operation = lines[2].toOperation(),
        divisibleBy = lines[3].toDivisibleBy(),
        nextMonkeyIfTrue = lines[4].toNextMonkey("true"),
        nextMonkeyIfFalse = lines[5].toNextMonkey("false"),
    )
}

private fun String.toNextMonkey(s: String): Int {
    return trim().replace("If $s: throw to monkey ", "").toInt()
}

private fun String.toDivisibleBy() =
    trim().replace("Test: divisible by ", "").toInt()

private fun String.toCode() =
    split(" ")[1].replace(":", "").toInt()

private fun String.toItems() =
    replace("Starting items: ", "")
        .split(", ")
        .map { it.trim() }
        .map { it.toInt() }

private fun String.toOperation(): Operation {
    val (op, n) = trim().replace("Operation: new = old ", "").split(" ")
    return when (op) {
        "*" -> Times(n.toOperand())
        "+" -> Plus(n.toOperand())
        else -> TODO()
    }
}

private fun String.toOperand(): Operand {
    return when (this) {
        "old" -> Old
        else -> N(toInt())
    }
}

data class Monkey(
    val code: Int,
    val items: List<Int>,
    val operation: Operation,
    val divisibleBy: Int,
    val nextMonkeyIfTrue: Int,
    val nextMonkeyIfFalse: Int,
    val itemsInspected: Int = 0
)

sealed interface Operation {
    val n: Operand
}

sealed interface Operand
data class N(val value: Int) : Operand
object Old : Operand

data class Times(override val n: Operand) : Operation
data class Plus(override val n: Operand) : Operation