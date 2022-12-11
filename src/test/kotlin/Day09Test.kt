
import Movement.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day09Test {
    @Test
    fun `head moves 1 to the right`() {
        val rope = Rope(
            head = p(left = 0, bottom = 0),
            tail = p(left = 0, bottom = 0)            
        )
        
        val nextRope = rope.moveHead(Right)
        
        assertEquals(
            Rope(
                head = p(left = 1, bottom = 0),
                tail = p(left = 0, bottom = 0)
            ),
            nextRope
        )
    }

    @Test
    fun `head moves 1 up`() {
        val rope = Rope(
            head = p(left = 0, bottom = 0),
            tail = p(left = 0, bottom = 0)
        )

        val nextRope = rope.moveHead(Up)

        assertEquals(
            Rope(
                head = p(left = 0, bottom = 1),
                tail = p(left = 0, bottom = 0)
            ),
            nextRope
        )
    }

    @Test
    fun `head moves 1 down`() {
        val rope = Rope(
            head = p(left = 0, bottom = 2),
            tail = p(left = 0, bottom = 3)
        )

        val nextRope = rope.moveHead(Down)

        assertEquals(
            Rope(
                head = p(left = 0, bottom = 1),
                tail = p(left = 0, bottom = 2)
            ),
            nextRope
        )
    }

    @Test
    fun `head moves 1 left`() {
        val rope = Rope(
            head = p(left = 2, bottom = 0),
            tail = p(left = 3, bottom = 0)
        )

        val nextRope = rope.moveHead(Left)

        assertEquals(
            Rope(
                head = p(left = 1, bottom = 0),
                tail = p(left = 2, bottom = 0)
            ),
            nextRope
        )
    }

    @Test
    fun `diagonal head moves 1 up right`() {
        val rope = Rope(
            head = p(left = 3, bottom = 2),
            tail = p(left = 2, bottom = 1)
        )

        val nextRope = rope.moveHead(Up)

        assertEquals(
            Rope(
                head = p(left = 3, bottom = 3),
                tail = p(left = 3, bottom = 2)
            ),
            nextRope
        )
    }

    @Test
    fun `diagonal head moves 1 up left`() {
        val rope = Rope(
            head = p(left = 2, bottom = 2),
            tail = p(left = 3, bottom = 1)
        )

        val nextRope = rope.moveHead(Up)

        assertEquals(
            Rope(
                head = p(left = 2, bottom = 3),
                tail = p(left = 2, bottom = 2)
            ),
            nextRope
        )
    }

    @Test
    fun `diagonal head moves 1 down left`() {
        val rope = Rope(
            head = p(left = 2, bottom = 2),
            tail = p(left = 3, bottom = 3)
        )

        val nextRope = rope.moveHead(Down)

        assertEquals(
            Rope(
                head = p(left = 2, bottom = 1),
                tail = p(left = 2, bottom = 2)
            ),
            nextRope
        )
    }

    @Test
    fun `diagonal head moves 1 down right`() {
        val rope = Rope(
            head = p(left = 2, bottom = 2),
            tail = p(left = 1, bottom = 3)
        )

        val nextRope = rope.moveHead(Down)

        assertEquals(
            Rope(
                head = p(left = 2, bottom = 1),
                tail = p(left = 2, bottom = 2)
            ),
            nextRope
        )
    }

    @Test
    fun `part 1 example`() {
        val input = """
            R 4
            U 4
            L 3
            D 1
            R 4
            D 1
            L 5
            R 2
        """.trimIndent()

        assertEquals(
            13,
            input.readMovements().tailPositions().size
        )
    }

    @Test
    fun `read movements`() {
        val input = """
            R 4
            U 4
            L 3
            D 1
        """.trimIndent()

        assertEquals(
            listOf(
                Right, Right, Right, Right,
                Up, Up, Up, Up,
                Left, Left, Left,
                Down
            ),
            input.readMovements()
        )
    }
    
    private fun p(left: Int, bottom: Int) = Position(left = left, bottom = bottom)
}

