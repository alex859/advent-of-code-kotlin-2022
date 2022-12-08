
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day08Test {
//    @Test
//    fun `one tree is visible`() {
//        val map = """
//            1
//        """.trimIndent()
//
//        assertEquals(
//            1,
//            map.visibleTreeCount()
//        )
//    }
//    
//    @Test
//    fun `all trees on the edges are visible`() {
//        val map = """
//            12
//            12
//        """.trimIndent()
//        
//        assertEquals(
//            4,
//            map.visibleTreeCount()
//        )
//    }
//
//    @Test
//    fun `all trees are visible - also inside`() {
//        val map = """
//            132
//            132
//        """.trimIndent()
//
//        assertEquals(
//            6,
//            map.visibleTreeCount()
//        )
//    }
//
//    @Test
//    fun `all trees are visible - more trees`() {
//        val map = """
//            132
//            357
//            132
//        """.trimIndent()
//
//        assertEquals(
//            9,
//            map.visibleTreeCount()
//        )
//    }
//
//    @Test
//    fun `tree in the middle is not visible`() {
//        val map = """
//            132
//            307
//            132
//        """.trimIndent()
//
//        assertEquals(
//            8,
//            map.visibleTreeCount()
//        )
//    }
    
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
}

internal fun List<Int>.leftOf(i: Int) =  dropLast(size - i)

internal fun List<Int>.rightOf(i: Int) =  drop(i + 1)

private fun List<Int>.visibleTrees(): Set<Int> {
    if (size == 1) {
        return setOf(0)
    }
    val left = mapIndexed { i, tree ->
        val leftOf = leftOf(i)
        i to  if (leftOf.isEmpty()) true
        else leftOf.count { it >= tree } == 0
    }.filter { (_, visible) -> visible }.map { (i, _) -> i }
    val right = mapIndexed { i, tree ->
        val rightOf = rightOf(i)
        i to if (rightOf.isEmpty()) true
        else rightOf.count { it >= tree } == 0
    }.filter { (_, visible) -> visible }.map { (i, _) -> i }
    return left.toSet() + right.toSet()
}

