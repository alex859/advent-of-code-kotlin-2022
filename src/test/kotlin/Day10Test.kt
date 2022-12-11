
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day10Test {
    @Test
    fun `value of cycle 0 with no op is 1`() {
        val input = """
            noop
        """.trimIndent()

        assertEquals(
            1,
            input.cycles()[0]
        )
    }

    @Test
    fun `value of cycle 1 with no op is 1`() {
        val input = """
            noop
            noop
        """.trimIndent()

        assertEquals(
            1,
            input.cycles()[1]
        )
    }

    @Test
    fun `value of cycle 3 is still 1`() {
        val input = """
            noop
            addx 3
        """.trimIndent()

        assertEquals(1, input.cycles()[0])
        assertEquals(1, input.cycles()[1])
        assertEquals(1, input.cycles()[2])
    }

    @Test
    fun `value of cycle 4 is still 1`() {
        val input = """
            noop
            addx 3
            addx -5
            noop
        """.trimIndent()

        assertEquals(1, input.cycles()[0])
        assertEquals(1, input.cycles()[1])
        assertEquals(1, input.cycles()[2])
        assertEquals(4, input.cycles()[3])
        assertEquals(4, input.cycles()[4])
        assertEquals(-1, input.cycles()[5])
    }

    @Test
    fun `signal strength`() {
        val input = listOf(1, 4, 5)

        assertEquals(1, input.signalStrength(cycle = 1))
        assertEquals(8, input.signalStrength(cycle = 2))
        assertEquals(15, input.signalStrength(cycle = 3))
    }

    @Test
    fun `draws something`() {
        val register = 1

        assertEquals("#", register.pixel(0))
        assertEquals("#", register.pixel(1))
        assertEquals("#", register.pixel(2))
        assertEquals(".", register.pixel(3))
    }

    @Test
    fun `draws something else`() {
        val input = """
            addx 15
            addx -11
            addx 6
            addx -3
            addx 5
            addx -1
            addx -8
            addx 13
            addx 4
            noop
            addx -1
        """.trimIndent()

        assertEquals("##..##..##..##..##..#", input.cycles().draw())
    }

    @Test
    fun `draws test`() {
        val input = """
addx 15
addx -11
addx 6
addx -3
addx 5
addx -1
addx -8
addx 13
addx 4
noop
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx -35
addx 1
addx 24
addx -19
addx 1
addx 16
addx -11
noop
noop
addx 21
addx -15
noop
noop
addx -3
addx 9
addx 1
addx -3
addx 8
addx 1
addx 5
noop
noop
noop
noop
noop
addx -36
noop
addx 1
addx 7
noop
noop
noop
addx 2
addx 6
noop
noop
noop
noop
noop
addx 1
noop
noop
addx 7
addx 1
noop
addx -13
addx 13
addx 7
noop
addx 1
addx -33
noop
noop
noop
addx 2
noop
noop
noop
addx 8
noop
addx -1
addx 2
addx 1
noop
addx 17
addx -9
addx 1
addx 1
addx -3
addx 11
noop
noop
addx 1
noop
addx 1
noop
noop
addx -13
addx -19
addx 1
addx 3
addx 26
addx -30
addx 12
addx -1
addx 3
addx 1
noop
noop
noop
addx -9
addx 18
addx 1
addx 2
noop
noop
addx 9
noop
noop
noop
addx -1
addx 2
addx -37
addx 1
addx 3
noop
addx 15
addx -21
addx 22
addx -6
addx 1
noop
addx 2
addx 1
noop
addx -10
noop
noop
addx 20
addx 1
addx 2
addx 2
addx -6
addx -11
noop
noop
noop
        """.trimIndent()

        assertEquals("""
            ##..##..##..##..##..##..##..##..##..##..
            ###...###...###...###...###...###...###.
            ####....####....####....####....####....
            #####.....#####.....#####.....#####.....
            ######......######......######......####
            #######.......#######.......#######.....
        """.trimIndent(), input.cycles().draw())
    }
}
