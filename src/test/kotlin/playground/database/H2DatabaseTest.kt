package playground.database

import org.h2.tools.Server
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.Test
import java.sql.Connection
import java.sql.DriverManager
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class H2DatabaseTest {

	@Test
	internal fun `connecting to a data source using DriverManager`() {

		val driver = "org.h2.Driver"

		Class.forName(driver)

		val connection = DriverManager.getConnection(
			"jdbc:h2:mem:",
			"sa",
			""
		)

		assertTrue(connection.isValid(1000))
	}

	@Test
	internal fun `creating a web server for an h2 in memory database to access the console`() {

		val driver = "org.h2.Driver"

		Class.forName(driver)

		val connection = DriverManager.getConnection(
			"jdbc:h2:mem:test_mem",
			"sa",
			""
		)

		/* The H2 console can be accessed at the URL localhost:8082 */

		val server = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start()

		println("This BREAKPOINT needs to be set to 'Suspend thread' to avoid suspending the web server thread!!")

		connection.close()
		server.stop()

		assertTrue(connection.isClosed)
		assertFalse(server.isRunning(false))
	}

	@Test
	internal fun `executing a query`() {

		val driver = "org.h2.Driver"

		Class.forName(driver)

		val connection = DriverManager.getConnection(
			"jdbc:h2:mem:test_mem",
			"sa",
			""
		)

		val initialTableCount = getTableCount(connection)

		assumeTrue(initialTableCount == 0)

		val statement = connection.createStatement()

		val query = """
					CREATE TABLE PERSON (
					firstname VARCHAR(255),
					lastname VARCHAR(255)
					)
		""".trimIndent()

		statement.executeUpdate(query)

		val finalTableCount = getTableCount(connection)

		assertEquals(1, finalTableCount)
	}
}

private fun getTableCount(connection: Connection): Int {
	val countTableStatement = connection.createStatement()

	val countTableQuery = """
			SELECT COUNT(*)
			FROM INFORMATION_SCHEMA.TABLES
			WHERE TABLE_TYPE = 'TABLE'
		""".trimIndent()

	return countTableStatement.executeQuery(countTableQuery).let {
		it.next()
		it.getInt(1)
	}
}