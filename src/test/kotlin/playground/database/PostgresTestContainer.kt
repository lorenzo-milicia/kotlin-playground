package playground.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.springframework.jdbc.core.JdbcTemplate
import org.testcontainers.containers.BindMode
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers


@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class PostgresTestContainer {

	protected lateinit var jdbcTemplate: JdbcTemplate

	init {
		container.start()
	}

	companion object {
		val container =
			PostgreSQLContainer("postgres:13")
				.withDatabaseName("testcontainer_db")
				.withUsername("user")
				.withPassword("password")
				.withInitScript("container/init.sql")
				.withFileSystemBind(
					"C:\\Users\\loren\\AppData\\Local\\Temp",
					"/home/user/tempfile",
					BindMode.READ_ONLY)
	}

	@BeforeAll
	internal fun setUp() {
		val config = HikariConfig()
		val jdbcContainer = container as JdbcDatabaseContainer<*>
		config.jdbcUrl = jdbcContainer.jdbcUrl
		config.username = jdbcContainer.username
		config.password = jdbcContainer.password
		config.driverClassName = jdbcContainer.driverClassName
		val datasource = HikariDataSource(config)
		jdbcTemplate = JdbcTemplate(datasource)
	}
}