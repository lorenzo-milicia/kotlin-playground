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
			valueTransform = { it.languageKey }
		).map { (name, languages) ->
			Person(
				name = Name(name.name, name.surname),
				spokenLanguages = languages.map {
					Language(it.languageName, it.languageAbbreviation)
				}
			)
		}

		val domainFromInterface =
			rows
				.map { it.toManyToOneRow() }
				.groupByKey { nameKey, languageKeys ->
					Person(
						name = Name(nameKey.name, nameKey.surname),
						spokenLanguages = languageKeys.map {
							Language(it.languageName, it.languageAbbreviation)
						}
					)
				}

		assertEquals(domain, domainFromInterface)

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

	private data class PersonRowKey(
		override val groupingKey: NameKey,
		override val manyToOneField: LanguageKey
	): ManyToOneRow<NameKey, LanguageKey>

	private data class NameKey(
		val name: String,
		val surname: String,
	)

	private data class LanguageKey(
		val languageName: String,
		val languageAbbreviation: String,
	)

	private val PersonRow.nameKey: NameKey
		get() = NameKey(name, surname)

	private val PersonRow.languageKey: LanguageKey
		get() = LanguageKey(spokenLanguage, spokenLanguageAbbreviation)

	private fun PersonRow.toManyToOneRow(): PersonRowKey =
		PersonRowKey(
			groupingKey = nameKey,
			manyToOneField = languageKey
		)

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

interface ManyToOneRow<K, G> {

	val groupingKey: K
	val manyToOneField: G
}

fun <R: ManyToOneRow<K, G>, K, G, T> List<R>.groupByKey(transform: (K, List<G>) -> T): List<T> =
	groupBy(
		keySelector = { it.groupingKey },
		valueTransform = { it.manyToOneField }
	).map { (key, list) ->
		transform(key, list)
	}