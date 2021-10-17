package playground.amqp

import org.junit.jupiter.api.Test
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.rabbit.test.context.SpringRabbitTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@SpringRabbitTest
class AqmpTest {

	@Autowired
	private lateinit var rabbit: AmqpTemplate

	@Test
	internal fun `sendind a message`() {

		rabbit.convertAndSend("Test", "Test", "Hello Rabbit!")
	}
}