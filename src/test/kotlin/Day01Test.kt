import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day01Test {
    @Test
    fun `one elf carrying one thing`() {
        val input = "1000"

        assertEquals(1000, input.maxTotalCalories())
    }

    @Test
    fun `one elf carrying multiple things`() {
        val input = """
            1000
            2500
            1500
        """.trimIndent()

        assertEquals(5000, input.maxTotalCalories())
    }

    @Test
    fun `return the max calories an elf is carrying`() {
        val input = """
            1000
            2500
            1500
            
            5000
            2500
        """.trimIndent()

        assertEquals(7500, input.maxTotalCalories())
    }

    @Test
    fun `return the total of the top 3 elves carried calories`() {
        val input = """
            1000
            2000
            3000
            
            4000
            
            5000
            6000
            
            7000
            8000
            9000
            
            10000
        """.trimIndent()

        assertEquals(45000, input.topThreeTotalCalories())
    }
}

