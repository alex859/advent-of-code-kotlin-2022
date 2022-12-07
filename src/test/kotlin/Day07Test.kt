
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day07Test {
    @Test
    fun `moves to root directory`() {
        val shell = Shell()
        
        shell.execute("cd /")
        
        assertEquals("/", shell.currentDirectory())
    }

    @Test
    fun `moves to another directory`() {
        val shell = Shell()

        shell.execute("cd a")

        assertEquals("a", shell.currentDirectory())
    }

    @Test
    fun `reads cd command`() {
        val input = "cd /"
        
        assertEquals(ChangeDir("/"), input.readCommand())
    }

    @Test
    fun `reads cd command a`() {
        val input = "cd a"

        assertEquals(ChangeDir("a"), input.readCommand())
    }
    
    @Test
    fun `read commands`() {
        val input = """
            ${'$'} cd /
            ${'$'} ls
            dir a
            14848514 b.txt
            8504156 c.dat
            dir d
            ${'$'} cd a
            ${'$'} cd ..
            ${'$'} cd ..
            ${'$'} ls
            dir e
            29116 f
            2557 g
            62596 h.lst
        """.trimIndent()

        val commands = input.readCommandsAsStrings()

        assertEquals(
            ListDir(
                listOf(
                    "dir a",
                    "14848514 b.txt",
                    "8504156 c.dat",
                    "dir d"
                )
            ),
            commands[1]
        )
        assertEquals(ChangeDir("a"), commands[2])
        assertEquals(ChangeDir(".."), commands[3])
        assertEquals(ChangeDir(".."), commands[4])

        assertEquals(
            ListDir(
                listOf(
                    "dir e",
                    "29116 f",
                    "2557 g",
                    "62596 h.lst"
                )
            ),
            commands[5]
        )
    }
    
    @Test
    fun `calculate size of directory`() {
        val dir = ListDir(
            listOf(
                "dir e",
                "29116 f",
                "2557 g",
                "62596 h.lst"
            )
        )

        assertEquals(94269, dir.size)
    }

    @Test
    fun `calculate size of nested directory`() {
        val commands = listOf(
            ChangeDir("/"),
            ListDir(
                listOf(
                    "dir a",
                    "29116 b"
                )
            ),
            ChangeDir("a"),
            ListDir(
                listOf(
                    "dir e",
                    "29116 f",
                    "2557 g",
                    "62596 h.lst"
                )
            )
        )

        assertEquals(
            94269,
            commands.sizeOf("/a")
        )
    }

    @Test
    fun `calculate size of root directory`() {
        val commands = listOf(
            ChangeDir("/"),
            ListDir(
                listOf(
                    "dir a",
                    "29116 b"
                )
            ),
            ChangeDir("a"),
            ListDir(
                listOf(
                    "dir e",
                    "29116 f",
                    "2557 g",
                    "62596 h.lst"
                )
            )
        )

        assertEquals(
            123385,
            commands.sizeOf("/")
        )
    }

    @Test
    fun `calculate size of each directory`() {
        val commands = listOf(
            ChangeDir("/"),
            ListDir(
                listOf(
                    "dir a",
                    "dir c",
                    "29116 b"
                )
            ),
            ChangeDir("a"),
            ListDir(
                listOf(
                    "dir e",
                    "dir h",
                    "29116 f",
                    "2557 g",
                    "62596 h.lst"
                )
            ),
            ChangeDir("e"),
            ListDir(
                listOf(
                    "62596 h.lst"
                )
            ),
            ChangeDir(".."),
            ChangeDir("c"),
            ListDir(
                listOf(
                    "10 h.lst"
                )
            ),
        )


        assertEquals(
            mapOf(
                "/" to 29116,
                "/a" to 94269,
                "/a/e" to 62596,
                "/a/c" to 10,
            ),
            commands.directories()
        )
    }

    @Test
    fun `calculate size root directories`() {
        val directories = mapOf(
            "/" to 5,
            "/a" to 12,
            "/a/e" to 2,
            "/a/c" to 20,
        )


        assertEquals(
            mapOf(
                "/" to 39,
                "/a" to 34,
                "/a/e" to 2,
                "/a/c" to 20,
            ),
            directories.sizes()
        )
    }

    @Test
    fun `calculate free space`() {
        val directories = mapOf(
            "/" to 5,
            "/a" to 12,
            "/a/e" to 2,
            "/a/c" to 20,
        )


        assertEquals(
            5,
            directories.freeSpace(total = 10)
        )
    }

    @Test
    fun `calculate unused`() {
        val directories = mapOf(
            "/" to 48381165,
            "/a" to 12,
            "/a/e" to 2,
            "/a/c" to 20,
        )


        assertEquals(
            8381165,
            directories.spaceToFree()
        )
    }
}


