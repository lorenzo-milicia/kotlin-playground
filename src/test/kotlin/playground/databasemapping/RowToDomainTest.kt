package playground.databasemapping

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RowToDomainTest {

	@Test
	internal fun `Mapping a one to many from rows to domain`() {
		val rows = listOf(
			PersonRow(
				name = "Lorenzo",
				surname = "Milos",
				spokenLanguage = "Italian",
				spokenLanguageAbbreviation = "IT"
			),
			PersonRow(
				name = "Lorenzo",
				surname = "Milos",
				spokenLanguage = "German",
				spokenLanguageAbbreviation = "DE"
			),
			PersonRow(
				name = "Lorenzo",
				surname = "Milos",
				spokenLanguage = "English",
				spokenLanguageAbbreviation = "EN"
			),
		)

		val domain = rows.groupBy(
			keySelector = { it.nameKey },
			valueTransform = { it.language }
		).map {(name, language) ->
			Person(
				name = Name(name.name, name.surname),
				spokenLanguages = language.map {
					Language(it.name, it.abbreviation)
				}
			)
		}

		assertEquals(1, domain.size)
		val person = domain.first()
		assertEquals(3, person.spokenLanguages.size)
	}

	data class PersonRow(
		val name: String,
		val surname: String,
		val spokenLanguage: String,
		val spokenLanguageAbbreviation: String,
	)


	private data class NameKey(
		val name: String,
		val surname: String,
	)

	private val PersonRow.nameKey: NameKey
		get() = NameKey(name, surname)

	private val PersonRow.language: Language
		get() = Language(spokenLanguage, spokenLanguageAbbreviation)

	data class Person(
		val name: Name,
		val spokenLanguages: List<Language>,
	)

	data class Name(
		val name: String,
		val surname: String,
	)

	data class Language(
		val name: String,
		val abbreviation: String,
	)
}