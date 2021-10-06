package classes

import org.junit.jupiter.api.Test
import javax.xml.crypto.Data
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class DataClassTest {

	@Test
	internal fun `standard class implementation of equals(), hashCode() and toString()`() {

		val person = Person(
			"Lorenzo",
			"Milicia",
			26,
		)

		val differentPerson = Person(
			"Lorenzo",
			"Milicia",
			26,
		)

		printObject(person, "person")
		printObject(differentPerson, "differentPerson")

		assertNotEquals(person, differentPerson)
		assertNotEquals(person.hashCode(), differentPerson.hashCode())
	}

	@Test
	internal fun `data class implementation of equals(), hashCode() and toString()`() {

		val person = DataPerson(
			"Lorenzo",
			"Milicia",
			26,
		)

		val differentPerson = DataPerson(
			"Lorenzo",
			"Milicia",
			26,
		)

		printObject(person, "person")
		printObject(differentPerson, "differentPerson")

		assertEquals(person, differentPerson)
		assertEquals(person.hashCode(), differentPerson.hashCode())
	}

	@Test
	internal fun `data class containing a non-data class as an attribute`() {

		val email = Email(
			"lorenzo.milicia",
			"test.com"
		)
		val differentEmail = Email(
			"lorenzo.milicia",
			"test.com"
		)

		val person = DataPersonWithEmail(
			"Lorenzo",
			"Milicia",
			26,
			email
		)

		val differentPerson = DataPersonWithEmail(
			"Lorenzo",
			"Milicia",
			26,
			differentEmail
		)

		printObject(email, "email")
		printObject(differentEmail, "differentEmail")

		printObject(person, "person")
		printObject(differentPerson, "differentPerson")

		assertNotEquals(email, differentEmail)
		assertNotEquals(person, differentPerson)

	}

	@Test
	internal fun `data class containing data classes as attributes`() {

		val email = DataEmail(
			"lorenzo.milicia",
			"test.com"
		)
		val differentEmail = DataEmail(
			"lorenzo.milicia",
			"test.com"
		)

		val person = DataPersonWithEmail(
			"Lorenzo",
			"Milicia",
			26,
			email
		)

		val differentPerson = DataPersonWithEmail(
			"Lorenzo",
			"Milicia",
			26,
			differentEmail
		)

		printObject(email, "email")
		printObject(differentEmail, "differentEmail")

		printObject(person, "person")
		printObject(differentPerson, "differentPerson")

		assertEquals(email, differentEmail)
		assertEquals(person, differentPerson)

	}
}

class Person(
	val firstName: String,      // --> never used
	val lastName: String,       // --> never used
	val age: Int,               // --> never used
)

data class DataPerson(
	val firstName: String,      // --> used in auto generated equals(), hashCode() and toString()
	val lastName: String,       // --> used in auto generated equals(), hashCode() and toString()
	val age: Int,               // --> used in auto generated equals(), hashCode() and toString()
)

data class DataPersonWithEmail(
	val firstName: String,
	val lastName: String,
	val age: Int,
	val email: IEmail,
)





interface IEmail

class Email(
	val localPart: String = "localpart",
	val domain: String = "domain.com"
): IEmail {
	override fun toString() = "$localPart@$domain"
}

data class DataEmail(
	val localPart: String = "localpart",
	val domain: String = "domain.com"
): IEmail {
	override fun toString() = "$localPart@$domain"
}




fun printObject(obj: Any, name: String) {
	println(
		"$name:\n" +
				"toString -> ${obj.toString()}\n" +
				"hashCode -> ${obj.hashCode()}\n" +
				"\n"
	)
}