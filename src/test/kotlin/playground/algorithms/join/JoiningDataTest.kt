package playground.algorithms.join

import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class JoiningDataTest {

	@Test
	internal fun `Joining two lists with a for loop`() {
		val size = 30_000

		val animals: List<Animal>
		val additionalInfo: List<AdditionalInfo>

		val setupTime = measureTimeMillis {
			animals = buildList<Animal> {
				for (i in 0..size) {
					add(Animal(i.toString(), "Name $i"))
				}
			}
			additionalInfo = buildList<AdditionalInfo> {
				for (i in 0..size) {
					add(AdditionalInfo(i.toString(), "Owner $i"))
				}

			}
		}

		val executionTime = measureTimeMillis {
			val joinedList = buildList<Pair<Animal, AdditionalInfo>> {
				animals.forEach { animal ->
					animal to additionalInfo.find { it.animalId == animal.id }
				}
			}

		}
		println("For each setup time: $setupTime ms")
		println("For each execution time: $executionTime ms")
	}

	@Test
	internal fun `Joining two maps`() {
		val size = 30_000

		val animals: Map<String, Animal>
		val additionalInfo: Map<String, AdditionalInfo>

		val setupTime = measureTimeMillis {
			animals = buildMap<String, Animal> {
				for (i in 0..size) {
					put(i.toString(), Animal(i.toString(), "Name $i"))
				}
			}
			additionalInfo = buildMap<String, AdditionalInfo> {
				for (i in 0..size) {
					put(i.toString(), AdditionalInfo(i.toString(), "Owner $i"))
				}

			}
		}

		val executionTime = measureTimeMillis {
			val joinedList = buildList<Pair<Animal, AdditionalInfo>> {
				animals.forEach { animal ->
					animal to additionalInfo[animal.key]
				}
			}

		}

		println("Map setup time: $setupTime ms")
		println("Map execution time: $executionTime ms")
	}

	data class Animal(
		val id: String,
		val name: String,
	)

	data class AdditionalInfo(
		val animalId: String,
		val ownerName: String,
	)
}