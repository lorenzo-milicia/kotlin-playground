package playground.channels

import org.junit.jupiter.api.Test
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

internal class ChannelsTest {


	@Test
	internal fun `Stream of objects`() {
		runBlocking {
			val channel = Channel<Something>()
			launch {
				fetchBatches(channel)
			}
			handleBatch(channel)
		}
	}


	private suspend fun fetchBatches(channel: Channel<Something>) {
		repeat(5) {
			channel.send(Something(it.toString(), "Something n. $it"))
			delay(1000)
		}
		channel.close()
	}

	private suspend fun handleBatch(channel: Channel<Something>) {
		channel.consumeEach {
			println("Got batch ${it.id}...working on it")
			delay(500)
			println("Done with batch ${it.id}")
		}
	}

	internal data class Something(
		val id: String,
		val name: String,
	)
}