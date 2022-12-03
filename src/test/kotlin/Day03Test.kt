import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day03Test {
    @Test
    fun `returns total priority`() {
        val rucksacks = """
            vJrwpWtwJgWrhcsFMMfFFhFp
            jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
            PmmdzqPrVvPwwTWBwg
            wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
            ttgJtRGJQctTZtZT
            CrZsJsPPZsGzwwsLwLmpwMDw
        """.trimIndent()
        
        assertEquals(157, rucksacks.itemsPriority())
    }
    
    @ParameterizedTest(name = "rucksack: {0}")
    @CsvSource(delimiter = '|', textBlock ="""
        vJrwpWtwJgWrhcsFMMfFFhFp | p
        jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL | L
        PmmdzqPrVvPwwTWBwg | P
        wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn | v
        ttgJtRGJQctTZtZT | t
        CrZsJsPPZsGzwwsLwLmpwMDw | s""")
    fun `return item type contained in both compartments`(rucksack: String, expected: Char) {
        assertEquals(expected, rucksack.itemInCommon())
    }

    @Test
    fun `compartments from rucksack`() {
        val input = "vJrwpWtwJgWrhcsFMMfFFhFp"
        
        val (compartment1, compartment2) = input.compartments
        
        assertEquals("vJrwpWtwJgWr", compartment1)
        assertEquals("hcsFMMfFFhFp", compartment2)
    }

    @ParameterizedTest(name = "type: {0}, priority: {1}")
    @CsvSource(delimiter = '|', textBlock ="""
        a | 1
        b | 2
        c | 3
        z | 26
        A | 27
        B | 28
        Z | 52""")
    fun priority(item: Char, priority: Int) {
        assertEquals(priority, item.priority)
    }

    @Test
    fun `calculates badge`() {
        val rucksacks = setOf(
            "vJrwpWtwJgWrhcsFMMfFFhFp",
            "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
            "PmmdzqPrVvPwwTWBwg",
        )

        assertEquals('r', rucksacks.badge)
    }
    
    @Test
    fun `divides into groups of 3`() {
        val rucksacks = listOf("a", "b", "c", "d", "e", "f", "g", "h", "j")
        
        assertIterableEquals(setOf(
            setOf("a", "b", "c"),
            setOf("d", "e", "f"),
            setOf("g", "h", "j"),
        ), rucksacks.groups)
    }

    @Test
    fun `returns badges priority`() {
        val rucksacks = """
            vJrwpWtwJgWrhcsFMMfFFhFp
            jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
            PmmdzqPrVvPwwTWBwg
            wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
            ttgJtRGJQctTZtZT
            CrZsJsPPZsGzwwsLwLmpwMDw
        """.trimIndent()

        assertEquals(70, rucksacks.badgesPriority())
    }
}