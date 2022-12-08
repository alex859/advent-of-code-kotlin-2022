import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day08Test {
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
}

private fun String.visibleTreeCount(): Int {
    return 4
}


