fun main() {
    val testInput = readInput("Day08_test.txt")
    check(testInput.visibleTrees() == 21)
    check(testInput.maxScore() == 8)

    val input = readInput("Day08.txt")
    println(input.visibleTrees())
    println(input.maxScore())
}


internal fun String.visibleTrees(): Int {
    val visibleInRows = this.rows().flatMapIndexed { i, row -> row.visibleTrees().map { i to it } }
    val visibleInColumns = this.columns().flatMapIndexed { i, col -> col.visibleTrees().map { it to i } }

    return (visibleInRows + visibleInColumns).distinct().size
}

internal fun String.rows(): List<List<Int>> {
    return lines().map { it.toList().map { tree -> tree.digitToInt() } }
}
internal fun String.columns(): List<List<Int>> {
    val columns = MutableList<MutableList<Int>>(lines().first().length) { mutableListOf() }
    lines().forEachIndexed { i, row ->
        row.forEachIndexed { j, tree ->
            val c = tree.digitToInt()
            val column = columns[j]
            column.add(c)
        }

    }
    return columns
}

internal fun List<Int>.leftOf(i: Int) =  dropLast(size - i)

internal fun List<Int>.rightOf(i: Int) =  drop(i + 1)

internal fun List<Int>.visibleTrees(): Set<Int> {
    if (size == 1) {
        return setOf(0)
    }
    val left = mapIndexed { i, tree ->
        val leftOf = leftOf(i)
        i to  if (leftOf.isEmpty()) true
        else leftOf.count { it >= tree } == 0
    }.filter { (_, visible) -> visible }.map { (i, _) -> i }
    val right = mapIndexed { i, tree ->
        val rightOf = rightOf(i)
        i to if (rightOf.isEmpty()) true
        else rightOf.count { it >= tree } == 0
    }.filter { (_, visible) -> visible }.map { (i, _) -> i }
    return left.toSet() + right.toSet()
}

internal fun String.maxScore(): Int {
    val rowCount = rows().count()
    val colCount = columns().count()
    return (0 until rowCount).flatMap { row ->
        (0 until colCount).map { col ->
            scenicScore(row, col)
        }
    }.max()
}

internal fun String.scenicScore(rowIndex: Int, colIndex: Int): Int {
    val row = rows()[rowIndex]
    val col = columns()[colIndex]
    val height = rows()[rowIndex][colIndex]
    val leftScore = row.leftOf(colIndex).reversed().scenicScoreForHeight(height)
    val rightScore = row.rightOf(colIndex).scenicScoreForHeight(height)
    val topScore = col.leftOf(rowIndex).reversed().scenicScoreForHeight(height)
    val downScore = col.rightOf(rowIndex).scenicScoreForHeight(height)

    return leftScore * rightScore * topScore * downScore
}

internal fun List<Int>.scenicScoreForHeight(height: Int): Int {
    if (isEmpty()) {
        return 0
    }
    val mapIndexed = mapIndexed { i, h -> i to h }
    val index = mapIndexed.filter { (_, h) -> h >= height }.firstOrNull()?.first?:(size - 1)
    return if (index == 0) 1 else index + 1
}
