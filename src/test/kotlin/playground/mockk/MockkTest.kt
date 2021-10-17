package playground.mockk

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MockkTest {

	@Test
	internal fun `Testing the System Under Test by mocking dependencies`() {

		val mockApple = mockk<Apple>()
		val mockOrange = mockk<Orange>()

		every { mockApple.intrinsicValue } returns 5
		every { mockOrange.intrinsicValue } returns 15

		val sut: FruitComputer = FruitComputer(mockApple, mockOrange)

		assertEquals(20, sut.addApplesAndOranges())
	}

	@Test
	internal fun `Argument matchers more and less`() {

		val mockApple = mockk<Apple>()

		every { mockApple.yearlyValue(more(2020)) } returns 100
		every { mockApple.yearlyValue(less(2020)) } returns 1

		assertEquals(100, mockApple.yearlyValue(2021))
		assertEquals(1, mockApple.yearlyValue(1995))
	}
}

class FruitComputer(
	private val apple: Apple,
	private val orange: Orange,
) {

	fun addApplesAndOranges(): Int = apple.intrinsicValue + orange.intrinsicValue
}

class Apple {

	val intrinsicValue: Int = 10

	fun yearlyValue(year: Int): Int {
		TODO("Complex Logic...")
	}
}

class Orange {

	val intrinsicValue: Int = 30

	fun yearlyValue(year: Int): Int {
		TODO("Complex Logic...")
	}
}