
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day08Test {
    @Test
    fun `one tree is always visible`() {
        val row = listOf(1)
        
        assertIterableEquals(listOf(0), row.visibleTrees())
    }

    @Test
    fun `two tree are always visible`() {
        val row = listOf(1, 2)

        assertIterableEquals(listOf(0, 1), row.visibleTrees())
    }

    @Test
    fun `all trees visible`() {
        val row = listOf(1, 2, 1)

        assertIterableEquals(listOf(0, 1, 2), row.visibleTrees())
    }

    @Test
    fun `short middle tree not visible`() {
        val row = listOf(1, 0, 1)

        assertIterableEquals(listOf(0, 2), row.visibleTrees())
    }

    @Test
    fun `short middle tree same height as others not visible`() {
        val row = listOf(1, 1, 1)

        assertIterableEquals(listOf(0, 2), row.visibleTrees())
    }

    @Test
    fun `visible from the left`() {
        val row = listOf(2, 3, 4)

        assertIterableEquals(listOf(0, 1, 2), row.visibleTrees())
    }
    
    @Nested
    inner class LeftOfTest {
        @Test
        fun `left of - a`() {
            val trees = listOf(1, 2, 3)

            assertEquals(
                listOf(1, 2),
                trees.leftOf(2)
            )
        }

        @Test
        fun `left of - b`() {
            val trees = listOf(1, 2, 3, 4)

            assertEquals(
                listOf(1, 2),
                trees.leftOf(2)
            )
        }
    }

    @Nested
    inner class RightOfTest {
        @Test
        fun `right of - a`() {
            val trees = listOf(1, 2, 3)

            assertEquals(
                emptyList<Int>(),
                trees.rightOf(2)
            )
        }

        @Test
        fun `right of - b`() {
            val trees = listOf(1, 2, 3, 4)

            assertEquals(
                listOf(4),
                trees.rightOf(2)
            )
        }

    }
    
   @Test
   fun `read a column`() {
       val input = """
           30373
           25512
           65332
           33549
           35390
       """.trimIndent()
       val columns = input.columns()
       
       assertEquals(
           listOf(3, 2, 6, 3, 3,),
           columns[0]
       )
       assertEquals(
           listOf(0, 5, 5, 3, 5,),
           columns[1]
       )
       assertEquals(
           listOf(3, 5, 3, 5, 3,),
           columns[2]
       )
   }

    @Test
    fun `read a row`() {
        val input = """
           30373
           25512
           65332
           33549
           35390
       """.trimIndent()
        val rows = input.rows()

        assertEquals(
            listOf(3, 0, 3, 7, 3,),
            rows[0]
        )
        assertEquals(
            listOf(2, 5, 5, 1, 2,),
            rows[1]
        )
    }

    @Test
    fun `visible trees`() {
        val input = """
           30373
           25512
           65332
           33549
           35390
       """.trimIndent()

        assertEquals(
            21,
            input.visibleTrees()
        )
    }
}


