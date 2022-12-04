fun main() {
    val testInput = readInput("Day04_test.txt")
    check(testInput.countFullyOverlappingAssignments() == 2)
    check(testInput.countPartialOverlappingAssignments() == 4)

    val input = readInput("Day04.txt")
    println(input.countFullyOverlappingAssignments())
    println(input.countPartialOverlappingAssignments())
}

internal fun String.countFullyOverlappingAssignments() =
    lines().map { it.toAssignmentPair() }.count { it.oneFullyContainsOther() }

internal fun String.countPartialOverlappingAssignments() =
    lines().map { it.toAssignmentPair() }.count { it.onePartiallyContainsOther() }

internal fun String.toAssignmentPair(): AssignmentPair {
    val (first, second) = split(",")
    return first.toIntRange() to second.toIntRange()
}

private fun String.toIntRange(): IntRange {
    val (lower, upper) = split("-")
    return lower.toInt()..upper.toInt()
}

internal fun AssignmentPair.oneFullyContainsOther() = (first in second) or (second in first)

internal fun AssignmentPair.onePartiallyContainsOther() = (first.overlaps(second)) or (second.overlaps(first))

internal typealias AssignmentPair = Pair<IntRange, IntRange>

internal infix operator fun IntRange.contains(that: IntRange) = this.toSet().containsAll(that.toSet())

internal fun IntRange.overlaps(that: IntRange) = this.toSet().intersect(that.toSet()).isNotEmpty()