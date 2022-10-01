package playground.channels

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import kotlinx.coroutines.channels.*

internal class ChannelsTest {

	@Test
	internal fun `Stream of objects`() {
		runBlocking {
			val batches = fetchBatches()
			batches.handleBatches()
		}
	}

	@OptIn(ExperimentalCoroutinesApi::class)
	private fun CoroutineScope.fetchBatches() = produce(capacity = 2) {
		repeat(5) {
			println("Fetching batch $it")
			delay(500)
			send(Something(it.toString(), "Something n. $it"))
			println("Batch $it sent")
		}
	}

	private suspend fun ReceiveChannel<Something>.handleBatches() {
		consumeEach {
			println("Got batch ${it.id}...working on it")
			delay(1000)
			println("Done with batch ${it.id}")
		}
	}

	internal data class Something(
		val id: String,
		val name: String,
	)
}