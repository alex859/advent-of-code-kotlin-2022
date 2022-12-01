fun main() {
    val testInput = readInput("Day01_test.txt")
    check(testInput.maxTotalCalories() == 24000)
    check(testInput.topThreeTotalCalories() == 45000)

    val input = readInput("Day01.txt")
    println(input.maxTotalCalories())
    println(input.topThreeTotalCalories())
}

fun String.maxTotalCalories(): Int = elves().maxOf { it.food().totalCalories() }

fun String.topThreeTotalCalories(): Int = elves().map { it.food().totalCalories() }.sortedDescending().take(3).sum()

private fun String.food() = split("\n")

private fun String.elves() = split("\n\n")

private fun List<String>.totalCalories(): Int = sumOf { it.toInt() }
