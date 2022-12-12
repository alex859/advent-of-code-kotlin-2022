
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day11Test {
    @Test
    fun `read monkey`() {
        val input = """
            Monkey 0:
              Starting items: 79, 98
              Operation: new = old * 19
              Test: divisible by 23
                If true: throw to monkey 2
                If false: throw to monkey 3
        """.trimIndent()
        
        val monkey = input.toMonkey()
        
        assertEquals(0, monkey.code)
        assertEquals(listOf(79, 98), monkey.items)
        assertEquals(Times(n = N(19)), monkey.operation)
        assertEquals(23, monkey.divisibleBy)
        assertEquals(2, monkey.nextMonkeyIfTrue)
        assertEquals(3, monkey.nextMonkeyIfFalse)
    }

    @Test
    fun `read monkey with addition`() {
        val input = """
            Monkey 0:
              Starting items: 79, 98
              Operation: new = old + 19
              Test: divisible by 23
                If true: throw to monkey 2
                If false: throw to monkey 3
        """.trimIndent()

        val monkey = input.toMonkey()

        assertEquals(Plus(n = N(19)), monkey.operation)
    }

    @Test
    fun `read monkey with multiplication with old`() {
        val input = """
            Monkey 0:
              Starting items: 79, 98
              Operation: new = old * old
              Test: divisible by 23
                If true: throw to monkey 2
                If false: throw to monkey 3
        """.trimIndent()

        val monkey = input.toMonkey()

        assertEquals(Times(n = Old), monkey.operation)
    }

    @Test
    fun `turn 1`() {
        val monkey0 = Monkey(
            code = 0,
            items = listOf(79, 98),
            operation = Times(N(19)),
            divisibleBy = 23,
            nextMonkeyIfTrue = 2,
            nextMonkeyIfFalse = 3
        )

        val monkey1 = Monkey(
            code = 1,
            items = listOf(54, 65, 75, 74),
            operation = Plus(N(69)),
            divisibleBy = 19,
            nextMonkeyIfTrue = 2,
            nextMonkeyIfFalse = 0
        )

        val monkey2 = Monkey(
            code = 2,
            items = listOf(79, 60, 97),
            operation = Times(Old),
            divisibleBy = 13,
            nextMonkeyIfTrue = 1,
            nextMonkeyIfFalse = 3
        )

        val monkey3 = Monkey(
            code = 3,
            items = listOf(74),
            operation = Plus(N(3)),
            divisibleBy = 17,
            nextMonkeyIfTrue = 0,
            nextMonkeyIfFalse = 1
        )
        
        val monkeys = listOf(monkey0, monkey1, monkey2, monkey3).playTurn(0)

        assertEquals(
            emptyList<Int>(),
            monkeys[0].items
        )
        assertEquals(monkey1, monkeys[1])
        assertEquals(monkey2, monkeys[2])
        assertEquals(
            2,
            monkeys[0].itemsInspected
        )
        assertEquals(
            listOf(74, 500, 620),
            monkeys[3].items
        )
    }


    @Test
    fun `round 1`() {
        val monkey0 = Monkey(
            code = 0,
            items = listOf(79, 98),
            operation = Times(N(19)),
            divisibleBy = 23,
            nextMonkeyIfTrue = 2,
            nextMonkeyIfFalse = 3
        )

        val monkey1 = Monkey(
            code = 1,
            items = listOf(54, 65, 75, 74),
            operation = Plus(N(6)),
            divisibleBy = 19,
            nextMonkeyIfTrue = 2,
            nextMonkeyIfFalse = 0
        )

        val monkey2 = Monkey(
            code = 2,
            items = listOf(79, 60, 97),
            operation = Times(Old),
            divisibleBy = 13,
            nextMonkeyIfTrue = 1,
            nextMonkeyIfFalse = 3
        )

        val monkey3 = Monkey(
            code = 3,
            items = listOf(74),
            operation = Plus(N(3)),
            divisibleBy = 17,
            nextMonkeyIfTrue = 0,
            nextMonkeyIfFalse = 1
        )

        val monkeys = listOf(monkey0, monkey1, monkey2, monkey3).playRound()

        assertEquals(
            listOf(20, 23, 27, 26),
            monkeys[0].items
        )
        assertEquals(
            listOf(2080, 25, 167, 207, 401, 1046),
            monkeys[1].items
        )
        assertEquals(
            listOf<Int>(),
            monkeys[2].items
        )
        assertEquals(
            listOf<Int>(),
            monkeys[3].items
        )
    }
}


