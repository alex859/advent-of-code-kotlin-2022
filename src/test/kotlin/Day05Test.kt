
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day05Test {
    @Test
    fun `read current stacks`() {
        val stacksStr = """
                [D]    
            [N] [C]    
            [Z] [M] [P]
             1   2   3
        """.trimIndent()

        val stacks = stacksStr.readStacks()

        assertEquals(3, stacks.size)
        assertEquals(listOf("N", "Z"), stacks[1])
        assertEquals(listOf("D", "C", "M"), stacks[2])
        assertEquals(listOf("P"), stacks[3])
    }

    @Test
    fun `only one stack`() {
        val stacksStr = """   
            [N]
            [Z]
             1
             """.trimIndent()

        val stacks = stacksStr.readStacks()

        assertEquals(listOf("N", "Z"), stacks[1])
    }

    @Test
    fun `two stacks`() {
        val stacksStr = """   
                [D]
            [N] [C]
            [Z] [M]
             1   2 
             """.trimIndent()

        val stacks = stacksStr.readStacks()

        assertEquals(listOf("N", "Z"), stacks[1])
        assertEquals(listOf("D", "C", "M"), stacks[2])
    }

    @Test
    fun `read a row`() {
        val stacksStr = "[N] [C]"

        val row = stacksStr.readRow()

        assertEquals(listOf("N", "C"), row)
    }

    @Test
    fun `read a row with nulls`() {
        val stacksStr = "    [C]"

        val row = stacksStr.readRow()

        assertEquals(listOf(null, "C"), row)
    }

    @Test
    fun `read a row with nulls and something`() {
        val stacksStr = "    [C]    "

        val row = stacksStr.readRow()

        assertEquals(listOf(null, "C", null), row)
    }
    
    @Test
    fun `read one instruction`() {
        val instruction = "move 1 from 2 to 1"
        
        assertEquals(
            Instruction(from = 2, to = 1, count = 1),
            instruction.readInstruction()
        )
    }

    @Test
    fun `read instructions`() {
        val message = """
                [D]    
            [N] [C]    
            [Z] [M] [P]
             1   2   3 
            
            move 1 from 2 to 1
            move 3 from 1 to 3
            move 2 from 2 to 1
            move 1 from 1 to 2
        """.trimIndent()

        assertEquals(
            listOf(
                Instruction(from = 2, to = 1, count = 1),
                Instruction(from = 1, to = 3, count = 3),
                Instruction(from = 2, to = 1, count = 2),
                Instruction(from = 1, to = 2, count = 1)
            ),
            message.readInstructions()
        )
    }
    
    @Test
    fun `apply instruction to crates - move one`() {
        val currentState = mapOf(
            1 to listOf("N", "Z"),
            2 to listOf("D", "C", "M"),
            3 to listOf("P"),
        )
        
        val result = currentState.apply(Instruction(count = 1, from = 2, to = 1))
        
        assertEquals(
            mapOf(
                1 to listOf("D", "N", "Z"),
                2 to listOf("C", "M"),
                3 to listOf("P"),
            ),
            result
        )
    }

    @Test
    fun `apply instruction to crates - move two`() {
        val currentState = mapOf(
            1 to listOf("N", "Z"),
            2 to listOf("D", "C", "M"),
            3 to listOf("P"),
        )

        val result = currentState.apply(Instruction(count = 2, from = 2, to = 1))

        assertEquals(
            mapOf(
                1 to listOf("C", "D", "N", "Z"),
                2 to listOf("M"),
                3 to listOf("P"),
            ),
            result
        )
    }

    @Test
    fun `apply multiple instructions to crates`() {
        val currentState = mapOf(
            1 to listOf("N", "Z"),
            2 to listOf("D", "C", "M"),
            3 to listOf("P"),
        )

        val result = currentState.apply(
            listOf(
                Instruction(count = 1, from = 2, to = 1),
                Instruction(count = 3, from = 1, to = 3),
                Instruction(count = 2, from = 2, to = 1),
                Instruction(count = 1, from = 1, to = 2),    
            )
        )

        assertEquals(
            mapOf(
                1 to listOf("C"),
                2 to listOf("M"),
                3 to listOf("Z", "N", "D", "P"),
            ),
            result
        )
    }

    @Test
    fun `read top of stacks`() {
        val currentState = mapOf(
            1 to listOf("C"),
            2 to listOf("M"),
            3 to listOf("Z", "N", "D", "P"),
        )

        assertEquals("CMZ", currentState.top())
    }
    
    @Test
    fun `apply instructions and read top of stacks`() {
        val input = """
                [D]    
            [N] [C]    
            [Z] [M] [P]
             1   2   3 
            
            move 1 from 2 to 1
            move 3 from 1 to 3
            move 2 from 2 to 1
            move 1 from 1 to 2
        """.trimIndent()
        
        assertEquals("CMZ", input.moveCrates())
    }


    @Test
    fun `read stacks from test`() {
        val input = """
                                    [Z] [W] [Z]
                    [D] [M]         [L] [P] [G]
                [S] [N] [R]         [S] [F] [N]
                [N] [J] [W]     [J] [F] [D] [F]
            [N] [H] [G] [J]     [H] [Q] [H] [P]
            [V] [J] [T] [F] [H] [Z] [R] [L] [M]
            [C] [M] [C] [D] [F] [T] [P] [S] [S]
            [S] [Z] [M] [T] [P] [C] [D] [C] [D]
             1   2   3   4   5   6   7   8   9
        """.trimIndent()

        assertEquals(
            listOf("Z", "L", "S", "F", "Q", "R", "P", "D"), 
            input.readStacks()[7]
        )
    }

    @Test
    fun `read row with consecutive nulls`() {
        val input = "                        [Z]"

        assertEquals(
            listOf(null, null, null, null, null, null, "Z"),
            input.readRow()
        )
    }
    
    @Test
    fun `read stacks from test input`() {
        val input = """
                [D]    
            [N] [C]    
            [Z] [M] [P]
             1   2   3 
            
            move 1 from 2 to 1
            move 3 from 1 to 3
            move 2 from 2 to 1
            move 1 from 1 to 2
        """.trimIndent()
        
        assertEquals(
            mapOf(
                1 to listOf("N", "Z"),
                2 to listOf("D", "C", "M"),
                3 to listOf("P"),
            ),
            input.readStacks()
        )
    }

    @Test
    fun `apply instruction to crates - move one with fancy strategy`() {
        val currentState = mapOf(
            1 to listOf("N", "Z"),
            2 to listOf("D", "C", "M"),
            3 to listOf("P"),
        )

        val result = currentState.applyFancy(Instruction(count = 1, from = 2, to = 1))

        assertEquals(
            mapOf(
                1 to listOf("D", "N", "Z"),
                2 to listOf("C", "M"),
                3 to listOf("P"),
            ),
            result
        )
    }

    @Test
    fun `apply instruction to crates - move two with fancy strategy`() {
        val currentState = mapOf(
            1 to listOf("D", "N", "Z"),
            2 to listOf("C", "M"),
            3 to listOf("P"),
        )

        val result = currentState.applyFancy(Instruction(count = 3, from = 1, to = 3))

        assertEquals(
            mapOf(
                1 to listOf(),
                2 to listOf("C", "M"),
                3 to listOf("D", "N", "Z", "P"),
            ),
            result
        )
    }
}
