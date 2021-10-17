package playground.database

import org.h2.jdbc.JdbcSQLSyntaxErrorException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.sql.DriverManager

class QueriesTest {

	@Test
	internal fun `can't use query precompilation to check for SQL syntactical errors`() {

		val driver = "org.h2.Driver"

		Class.forName(driver)

		val connection = DriverManager.getConnection(
			"jdbc:h2:mem:test_mem;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE",
			"sa",
			""
		)

		val query = """
					SELECT *
					FROM Person
		""".trimIndent()

		assertThrows<JdbcSQLSyntaxErrorException> { connection.prepareStatement(query) }
	}
}