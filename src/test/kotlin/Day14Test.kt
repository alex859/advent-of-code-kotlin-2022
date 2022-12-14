
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day14Test {
    @Test
    fun `sand unit goes down`() {
        val sand = emptyList<Tile>()

        val result = WaterFall(rocks = rocks, sand = sand).addSand().wait()
        
        assertEquals(
            listOf(500 to 8),
            result.sand
        )
    }

    @Test
    fun `sand unit goes down 2`() {
        val sand = listOf(500 to 8)

        val result = WaterFall(rocks = rocks, sand = sand).addSand().wait()

        assertEquals(
            listOf(
                499 to 8,
                500 to 8
            ),
            result.sand
        )
    }

    @Test
    fun `sand unit goes down 3`() {
        val sand = listOf(499 to 8, 500 to 8)

        val result = WaterFall(rocks = rocks, sand = sand).addSand().wait()

        assertEquals(
            listOf(
                501 to 8,
                499 to 8,
                500 to 8
            ),
            result.sand
        )
    }

    @Test
    fun `sand unit goes down 4`() {
        val sand = listOf(
            501 to 8,
            499 to 8,
            500 to 8
        )

        val result = WaterFall(rocks = rocks, sand = sand).addSand().wait()

        assertEquals(
            listOf(
                500 to 7,
                501 to 8,
                499 to 8,
                500 to 8
            ),
            result.sand
        )
    }

    @Test
    fun `sand unit goes down 5`() {
        val sand = listOf(
            500 to 7,
            501 to 8,
            499 to 8,
            500 to 8
        )

        val result = WaterFall(rocks = rocks, sand = sand).addSand().wait()

        assertEquals(
            listOf(
                498 to 8,
                500 to 7,
                501 to 8,
                499 to 8,
                500 to 8
            ),
            result.sand
        )

        result.print()
    }

    @Test
    fun `sand unit goes down 22`() {
        val start = WaterFall(rocks = rocks, sand = emptyList())
        
        val result = start.play()
        result.print()
        assertEquals(24, result.sand.size)
    }

    @Test
    fun `read a line`() {
        val input = "498,4 -> 498,6"
        
        assertEquals(
            setOf(
                498 to 4,
                498 to 5,
                498 to 6,
            ),
            input.readRocks().toSet()
        )
    }

    @Test
    fun `read a line 2`() {
        val input = "498,6 -> 498,4"

        assertEquals(
            setOf(
                498 to 4,
                498 to 5,
                498 to 6,
            ),
            input.readRocks().toSet()
        )
    }

    @Test
    fun `read a line horizontal`() {
        val input = "498,6 -> 496,6"

        assertEquals(
            setOf(
                498 to 6,
                497 to 6,
                496 to 6,
            ),
            input.readRocks().toSet()
        )
    }

    @Test
    fun `read a line horizontal right`() {
        val input = "496,6 -> 498,6"

        assertEquals(
            setOf(
                498 to 6,
                497 to 6,
                496 to 6,
            ),
            input.readRocks().toSet()
        )
    }

    @Test
    fun `read a line horizontal and vertical`() {
        val input = "498,4 -> 498,6 -> 496,6"

        assertEquals(
            setOf(
                498 to 4,
                498 to 5,
                498 to 6,
                497 to 6,
                496 to 6,
            ),
            input.readRocks().toSet()
        )
    }

    @Test
    fun `read more lines`() {
        val input = """
            498,4 -> 498,6 -> 496,6
            503,4 -> 502,4 -> 502,9 -> 494,9
        """.trimIndent()
        
        val result = input.lines().flatMap { it.readRocks() }
        
        assertEquals(
            rocks.toSet(), result.toSet()
        )
    }

    private val rocks = listOf(
        494 to 9,
        495 to 9,
        496 to 6,
        496 to 9,
        497 to 6,
        497 to 9,
        498 to 4,
        498 to 5,
        498 to 6,
        498 to 9,
        499 to 9,
        500 to 9,
        501 to 9,
        502 to 4,
        502 to 5,
        502 to 6,
        502 to 7,
        502 to 8,
        502 to 9,
        503 to 4,
    )
}





