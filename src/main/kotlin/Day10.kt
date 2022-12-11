fun main() {
    val testInput = readInput("Day10_test.txt")
    check(testInput.day10Part1() == 13140)
    check(testInput.day10Part2() == """
        ##..##..##..##..##..##..##..##..##..##..
        ###...###...###...###...###...###...###.
        ####....####....####....####....####....
        #####.....#####.....#####.....#####.....
        ######......######......######......####
        #######.......#######.......#######.....
    """.trimIndent())

    val input = readInput("Day10.txt")
    println(input.day10Part1())
    println(input.day10Part2())
}

internal fun String.day10Part1(): Int {
    val cycles = cycles()
    return cycles.signalStrength(20) + 
            cycles.signalStrength(60) + 
            cycles.signalStrength(100) + 
            cycles.signalStrength(140) +
            cycles.signalStrength(180) +
            cycles.signalStrength(220)
}

internal fun String.day10Part2() = cycles().draw()

internal fun Int.pixel(index: Int): String {
    val sprite = listOf(this - 1, this, this + 1)
    return if (sprite.contains(index)) "#" else "."
}

internal fun List<Int>.draw(): String {
    return chunked(40).joinToString("\n") { it.drawLine() }
}

private fun List<Int>.drawLine() = mapIndexed { i, register ->
    register.pixel(i)
}.joinToString(separator = "")

internal fun List<Int>.signalStrength(cycle: Int): Int {
    return this[cycle - 1] * cycle
}

internal fun String.cycles(): List<Int> {
    var register = 1
    return lines()
        .map { it.toCpuInstruction() }
        .flatMap { instruction ->
            when (instruction) {
                is NoOp -> listOf(register)
                is Add -> listOf(register, register).also { register += instruction.addend }
            }
        }
}

private fun String.toCpuInstruction() = when (this) {
    "noop" -> NoOp
    else -> split(" ").let { (_, addend) -> Add(addend.toInt()) }
}

private sealed interface CpuInstruction
object NoOp : CpuInstruction
data class Add(val addend: Int) : CpuInstruction