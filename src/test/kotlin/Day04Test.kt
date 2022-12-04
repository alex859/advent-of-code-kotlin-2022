import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day04Test {
    
    @Test
    fun `create pairs from string`() {
        val str = "2-8,3-7"
        
        assertEquals(2..8 to 3..7, str.toAssignmentPair())
    }
    
    @Test
    fun `a range contains another range`() {
        val first = 1..10
        val second = 2..9
        
        assertFalse(first in second)
        assertTrue(second in first)
    }

    @Test
    fun `a range contains another range - same`() {
        val first = 1..10
        val second = 1..10

        assertTrue(first in second)
        assertTrue(second in first)
    }

    @Test
    fun `a range contains another range - first included`() {
        val first = 1..9
        val second = 1..10

        assertTrue(first in second)
        assertFalse(second in first)
    }

    @Test
    fun `a range contains another range - last included`() {
        val first = 2..10
        val second = 1..10

        assertTrue(first in second)
        assertFalse(second in first)
    }
    
    @Test
    fun `one assignment pair contains another`() {
        assertTrue((2..10 to 1..10).oneFullyContainsOther())
        assertTrue((1..10 to 2..10).oneFullyContainsOther())
        assertFalse((3..7 to 2..5).oneFullyContainsOther())
    }
    
    @Test
    fun `count how may assignment where one fully contains the other`() {
        val assignments = """
            2-4,6-8
            2-3,4-5
            5-7,7-9
            2-8,3-7
            6-6,4-6
            2-6,4-8
        """.trimIndent()
        
        assertEquals(2, assignments.countFullyOverlappingAssignments())
    }

    @Test
    fun `a range partially contains another range - same`() {
        val first = 1..10
        val second = 1..10

        assertTrue(first.overlap(second))
    }

    @Test
    fun `a range partially contains another range`() {
        val first = 5..7
        val second = 7..9

        assertTrue(first.overlap(second))
    }

    @Test
    fun `a range partially contains another range - single`() {
        val first = 6..6
        val second = 4..6

        assertTrue(first.overlap(second))
    }

    @Test
    fun `a range partially contains another range - more`() {
        val first = 2..6
        val second = 4..8

        assertTrue(first.overlap(second))
    }

    @Test
    fun `count how may assignment where one partially contains the other`() {
        val assignments = """
            2-4,6-8
            2-3,4-5
            5-7,7-9
            2-8,3-7
            6-6,4-6
            2-6,4-8
        """.trimIndent()

        assertEquals(4, assignments.countPartialOverlappingAssignments())
    }
}

private fun IntRange.overlap(that: IntRange): Boolean {
    return this.toSet().intersect(that.toSet()).isNotEmpty()
}

