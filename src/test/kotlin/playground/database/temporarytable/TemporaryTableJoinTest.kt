package playground.database.temporarytable

import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import playground.database.PostgresTestContainer
import java.nio.file.Files
import kotlin.io.path.createTempFile
import kotlin.system.measureTimeMillis

class TemporaryTableJoinTest: PostgresTestContainer() {

	private val resultSize = 100_000

	@Test
	@Order(1)
	internal fun `Temporary table from CSV`() {
		val codes = (0..resultSize).toList().map { it.toString() }

		val file = createTempFile(
			prefix = "codes_table",
			suffix = ".csv"
		)

		measureTimeMillis {
			Files.write(file, csvString(codes).toByteArray())
			jdbcTemplate.execute(tableFromCsvQuery("/home/user/tempfile/${file.fileName}"))
		}.also { println("CREATING TABLE took $it ms") }

		val count: Int?
		val timeCount = measureTimeMillis {
			count = jdbcTemplate.queryForObject(
				"SELECT COUNT(*) FROM article art join temp_table tmp on art.code = tmp.code;"
			) { rs, _ -> rs.getInt(1) }
		}
		println("COUNT result: $count - took $timeCount ms")

		jdbcTemplate.execute("DROP TABLE temp_table;")
	}

	@Test
	@Order(1)
	internal fun `Temporary table from bulk insert`() {
		val codes = (0..resultSize).toList().map { it.toString() }

		measureTimeMillis {
			jdbcTemplate.execute(insertFromList(codes))
		}.also { println("CREATING AND POPULATING TABLE took $it ms") }

		val count: Int?
		val timeCount = measureTimeMillis {
			count = jdbcTemplate.queryForObject(
				"SELECT COUNT(*) FROM article art join temp_table tmp on art.code = tmp.code;"
			) { rs, _ -> rs.getInt(1) }
		}
		println("COUNT result: $count - took $timeCount ms")

		jdbcTemplate.execute("DROP TABLE temp_table;")
	}

	@Test
	internal fun `IN clause`() {
		val codes = (0..resultSize).toList().map { it.toString() }

		val count: Int?
		val timeCount = measureTimeMillis {
			count = jdbcTemplate.queryForObject(
				"""
				SELECT COUNT(*) FROM article 
				WHERE code IN (values ${codes.joinToString(",") { "('$it')" }});
				""".trimIndent()
			) { rs, _ -> rs.getInt(1) }
		}
		println("COUNT result: $count - took $timeCount ms")
	}

	private fun tableFromCsvQuery(pathString: String): String =
		"""
			CREATE TEMPORARY TABLE temp_table(
			   code varchar
			);
			COPY temp_table
			FROM '$pathString' 
			DELIMITER ',' 
			CSV HEADER;
		""".trimIndent()

	private fun insertFromList(codes: List<String>): String =
		"""
			CREATE TEMPORARY TABLE temp_table(
			   code varchar
			);
			
			INSERT INTO temp_table (code)
			VALUES
				${codes.joinToString(",\n") { "('$it')" }};
		""".trimIndent()

	private fun csvString(codes: List<String>): String =
		"""
			CODE
			${codes.joinToString("\n") { it }}
		""".trimIndent()
}