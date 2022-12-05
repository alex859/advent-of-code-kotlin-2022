fun main() {
    val testInput = readInput("Day05_test.txt")
    check(testInput.moveCrates() == "CMZ")
    check(testInput.moveCratesFancy() == "MCD")

    val input = readInput("Day05.txt")
    println(input.moveCrates())
    println(input.moveCratesFancy())
}


internal fun String.moveCrates(): String {
    val stacks = readStacks()
    val instructions = readInstructions()

    return stacks.apply(instructions).top()
}

internal fun String.moveCratesFancy(): String {
    val stacks = readStacks()
    val instructions = readInstructions()

    return stacks.applyFancy(instructions).top()
}

internal fun Map<Int, List<String>>.top() =
    toSortedMap().values.joinToString(separator = "") { it.first() }

internal fun Map<Int, List<String>>.apply(instruction: Instruction): Map<Int, List<String>> {
    val mutable = this.toMutableMap()
    val from = this[instruction.from] ?: error("unable to find stack ${instruction.from}")
    val to = this[instruction.to] ?: error("unable to find stack ${instruction.from}")
    val (updatedFrom, updatedTo) = moveElements(instruction.count, source = from, destination = to)
    mutable += instruction.from to updatedFrom
    mutable += instruction.to to updatedTo

    return mutable
}

internal fun Map<Int, List<String>>.applyFancy(instruction: Instruction): Map<Int, List<String>> {
    val mutable = this.toMutableMap()
    val from = this[instruction.from] ?: error("unable to find stack ${instruction.from}")
    val to = this[instruction.to] ?: error("unable to find stack ${instruction.from}")
    val (updatedFrom, updatedTo) = moveElementsFancy(instruction.count, source = from, destination = to)
    mutable += instruction.from to updatedFrom
    mutable += instruction.to to updatedTo
    return mutable
}

private fun moveElements(count: Int, source: List<String>, destination: List<String>): Pair<List<String>, List<String>> {
    val mutableSource = source.toMutableList()
    val mutableDestination = destination.toMutableList()
    repeat(count) {
        val element = mutableSource.removeAt(0)
        mutableDestination.add(0, element)
    }
    
    return mutableSource.toList() to (mutableDestination.toList())
}

private fun moveElementsFancy(count: Int, source: List<String>, destination: List<String>): Pair<List<String>, List<String>> {
    val cratesToMove = source.take(count)
    return source.drop(count) to (cratesToMove + destination)
}


internal fun Map<Int, List<String>>.apply(instructions: List<Instruction>): Map<Int, List<String>> {
    return instructions.fold(this) { current, instructions -> current.apply(instructions) }
}

internal fun Map<Int, List<String>>.applyFancy(instructions: List<Instruction>): Map<Int, List<String>> {
    return instructions.fold(this) { current, instructions -> current.applyFancy(instructions) }
}

internal fun String.readInstructions(): List<Instruction> {
    val (_, instructions) = split("\n\n")
    return instructions.lines().map { it.readInstruction() }
}

internal fun String.readInstruction(): Instruction {
    val regex = "move (\\d+?) from (\\d+?) to (\\d+?)".toRegex()
    val (count, from, to) = regex.find(this)?.destructured ?: error("unable to read instruction $this")
    return Instruction(from = from.toInt(), to = to.toInt(), count = count.toInt())
}

internal fun String.readRow(): List<String?> {
    return this.chunked(4).map {
        if (it.contains("[")) {
            it.replace("[", "").replace("]", "").trim()
        } else {
            null
        }
    }
}

internal fun String.readStacks(): Map<Int, List<String>> {
    val (stacks, _) = split("\n\n")
    val lines = stacks.lines()
    val result = mutableMapOf<Int, MutableList<String>>()
    lines.exceptLast().forEach {
        it.readRow().mapIndexed { i, element ->
            val current = result.computeIfAbsent(i + 1) { mutableListOf() }
            if (element != null) {
                current += element
            }
        }
    }

    return result
}

private fun List<String>.exceptLast() = subList(0, size - 1)

data class Instruction(
    val from: Int,
    val to: Int,
    val count: Int
)