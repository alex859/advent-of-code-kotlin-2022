fun main() {
    val testInput = readInput("Day07_test.txt")
    check(testInput.result() == 95437)
    check(testInput.result2() == 24933642)

    val input = readInput("Day07.txt")
    println(input.result())
    println(input.result2())
}

fun String.result(): Int {
    return readCommandsAsStrings().directories().sizes().values.filter { it <= 100000 }.sum()
}

fun String.result2(): Int {
    val directories = readCommandsAsStrings().directories()
    val values = directories.sizes().values
    val sizeOfOuterMost = directories.sizes()["/"] ?: error("cannot find root")
    val freeSpace = 70000000 - sizeOfOuterMost
    val spaceToFree = 30000000 - freeSpace
    return values
        .filter { it >= spaceToFree }
        .min()
}

fun Map<String, Int>.sizes(): Map<String, Int> {
    val allDirectories = keys
    return allDirectories.associateWith {
        filter { (key, _) -> key.startsWith(it) }.map { (_, value) -> value }.sum()
    }
}

fun Map<String, Int>.spaceToFree(): Int = 30000000 - freeSpace(total = 70000000)
fun Map<String, Int>.freeSpace(total: Int): Int = total - (this["/"]?: error("cannot find root"))

fun List<Command>.sizeOf(dir: String): Int {
    return chunked(2).map { (cd, ls) ->
        when (cd) {
            is ChangeDir -> when (ls) {
                is ListDir -> cd.dir to ls
                else -> TODO("expecting $ls to be ListDir")
            }

            else -> TODO("expecting $cd to be ChangeDir")
        }
    }.filter { (myDir, _) -> myDir == dir }
        .map { (_, ls) -> ls.size }
        .first()
}

fun List<Command>.directories(): MutableMap<String, Int> {
    val directories = mutableMapOf<String, Int>()
    var currentDirectory = ""
    forEach { command ->
        when (command) {
            is ChangeDir -> {
                currentDirectory = currentDirectory.change(command.dir)
            }
            is ListDir -> {
                directories += currentDirectory to command.size
            }
        }
    }
    return directories
}

private fun String.change(dir: String): String =
    when (dir) {
        "/" -> "/"
        ".." -> split("/").toList().dropLast(1).joinToString("/")
        else -> this + (if (this == "/") dir else "/$dir")
    }

internal fun String.readCommandsAsStrings() =
    split("$ ")
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .map { it.readCommand() }

fun String.readCommand(): Command {
    return if (startsWith("cd")) {
        val (_, dir) = split(" ")
        ChangeDir(dir)
    } else if (startsWith("ls")) {
        ListDir(lines().drop(1))
    } else {
        TODO()
    }
}

sealed interface Command
data class ChangeDir(val dir: String): Command
data class ListDir(val files: List<String>): Command {
    val size: Int get() {
        return files
            .filter { !it.startsWith("dir ") }
            .sumOf {
                val (size, _) = it.split(" ")
                size.toInt()
            }
    }
}

class Shell {
    private var currentDirectory = "/"
    fun execute(cmd: String) {
        val (_, dir) = cmd.split(" ")
        this.currentDirectory = dir
    }

    fun currentDirectory(): String {
        return currentDirectory
    }
}