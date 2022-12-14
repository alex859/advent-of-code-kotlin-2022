
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day13Test {
    @Test
    fun `read a simple list`() {
        val input = "[1,1,3,1,1]"
        
        assertEquals(
            listOf(1, 1, 3, 1, 1),
            input.toPacketList()
        )
    }

    @Test
    fun `reads empty list`() {
        val input = "[]"

        assertEquals(
            listOf<Any>(),
            input.toPacketList()
        )
    }
    
    @Test
    fun `read a simple list 2`() {
        val input = "[[1]]"

        assertEquals(
            listOf(listOf(1)),
            input.toPacketList()
        )
    }

    @Test
    fun `read a nested list`() {
        val input = "[[1],[2,3,4]]"

        assertEquals(
            listOf(listOf(1), listOf(2, 3 , 4)),
            input.toPacketList()
        )
    }    
    
    @Test
    fun `read another nested list`() {
        val input = "[1,[2,[3,[4,[5,6,7]]]],8,9]"

        assertEquals(
            listOf(
                1,
                listOf(2, listOf(3, listOf(4, listOf(5, 6, 7)))),
                8,
                9
            ),
            input.toPacketList()
        )
    }

    @Test
    fun `read a nested list with 10`() {
        val input = "[10]"

        assertEquals(
            listOf(10),
            input.toPacketList()
        )
    }

    @Test
    fun `are in right order`() {
        val left = "[1,1,3,1,1]".toPacketList()
        val right = "[1,1,5,1,1]".toPacketList()

        assertTrue(left < right)
    }

    @Test
    fun `are in right order list`() {
        val left = "[[2]]".toPacketList()
        val right = "[[3]]".toPacketList()

        assertTrue(left < right)
    }

    @Test
    fun `are in right order list 2`() {
        val left = "[[1],[2,3,4]]".toPacketList()
        val right = "[[1],4]".toPacketList()

        assertTrue(left < right)
    }

    @Test
    fun `are in right order list 3`() {
        val left = "[9]".toPacketList()
        val right = "[[8,7,6]]".toPacketList()

        assertFalse(left < right)
    }

    @Test
    fun `are in right order list 4`() {
        val left = "[[4,4],4,4]".toPacketList()
        val right = "[[4,4],4,4,4]".toPacketList()

        assertTrue(left < right)
    }

    @Test
    fun part1() {
        val input = """
            [1,1,3,1,1]
            [1,1,5,1,1]

            [[1],[2,3,4]]
            [[1],4]

            [9]
            [[8,7,6]]

            [[4,4],4,4]
            [[4,4],4,4,4]

            [7,7,7,7]
            [7,7,7]

            []
            [3]

            [[[]]]
            [[]]

            [1,[2,[3,[4,[5,6,7]]]],8,9]
            [1,[2,[3,[4,[5,6,0]]]],8,9]
        """.trimIndent()

        assertEquals(13, input.day13Part1())
    }
}




