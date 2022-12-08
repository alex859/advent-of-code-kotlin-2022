import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day08Test {
    @Test
    fun `one tree is visible`() {
        val map = """
            1
        """.trimIndent()

        Assertions.assertEquals(
            1,
            map.visibleTreeCount()
        )
    }
    
    @Test
    fun `all trees on the edges are visible`() {
        val map = """
            12
            12
        """.trimIndent()
        
        Assertions.assertEquals(
            4,
            map.visibleTreeCount()
        )
    }

    @Test
    fun `all trees are visible - also inside`() {
        val map = """
            132
            132
        """.trimIndent()

        Assertions.assertEquals(
            6,
            map.visibleTreeCount()
        )
    }

    @Test
    fun `all trees are visible - more trees`() {
        val map = """
            132
            357
            132
        """.trimIndent()

        Assertions.assertEquals(
            9,
            map.visibleTreeCount()
        )
    }

    @Test
    fun `tree in the middle is not visible`() {
        val map = """
            132
            307
            132
        """.trimIndent()

        Assertions.assertEquals(
            8,
            map.visibleTreeCount()
        )
    }
}

private fun String.visibleTreeCount(): Int {
    return lines().flatMap { it.toList() }.size
}


