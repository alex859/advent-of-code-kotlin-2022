
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource


class Day06Test {
    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource(delimiter = '|', textBlock = """
        mjqjpqmgbljsphdztnvjfqwrcgsmlb | 7
        bvwbjplbgvbhsrlpgdmjqwftvncz | 5
        nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg | 10
        zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw | 11""")
    fun `start of packet position`(message: String, expected: Int) {
        assertEquals(expected, message.getStartOfPacket(markerSize = 4))
    }
    
    @Test
    fun `start of packet is right at the start`() {
        val message = "jpqmgbljsphdz"
        
        assertEquals(4, message.getStartOfPacket(markerSize = 4))
    }

    @Test
    fun `marker starts after 1 character`() {
        val message = "jjpqmgbljsphdz"

        assertEquals(5, message.getStartOfPacket(markerSize = 4))
    }
    @Test
    fun `marker starts after 3 character`() {
        val message = "ababpqmgbljsphdz"

        assertEquals(6, message.getStartOfPacket(markerSize = 4))
    }

    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource(delimiter = '|', textBlock = """
        mjqjpqmgbljsphdztnvjfqwrcgsmlb | 19
        bvwbjplbgvbhsrlpgdmjqwftvncz | 23
        nppdvjthqldpwncqszvftbrmjlhg | 23
        nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg | 29
        zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw | 26""")
    fun `start of packet position with bigger marker size`(message: String, expected: Int) {
        assertEquals(expected, message.getStartOfPacket(markerSize = 14))
    }
}


