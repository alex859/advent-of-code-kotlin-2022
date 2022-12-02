import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day02Test {
    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource(delimiter = '|', textBlock = """
        A X | 4
        B X | 1
        C X | 7
        A Y | 8
        B Y | 5
        C Y | 2
        A Z | 3
        B Z | 9
        C Z | 6""")
    fun `calculate score for single round`(input: String, expected: Int) {
        assertEquals(expected, input.score())
    }
    
    @Test
    fun `calculates result for multiple rounds`() {
        val input = """
            A Y
            B X
            C Z
        """.trimIndent()
        
        assertEquals(15, input.total { it.score() } )
    }

    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource(delimiter = '|', textBlock = """
        A X | 3
        B X | 1
        C X | 2
        A Y | 4
        B Y | 5
        C Y | 6
        A Z | 8
        B Z | 9
        C Z | 7""")
    fun `calculate score for update rule`(input: String, expected: Int) {
        assertEquals(expected, input.updatedScore())
    }
}

