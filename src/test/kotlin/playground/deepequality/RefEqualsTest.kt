package playground.deepequality

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RefEqualsTest {

	@Test
	internal fun `referential equals`() {
		val productOne = Product().apply {
			name = "First"
			companyInfo = CompanyInfo().apply {
				name = "FirstCompany"
			}
		}
		val productTwo = Product().apply {
			name = "First"
			companyInfo = CompanyInfo().apply {
				name = "FirstCompany"
			}
		}
		assertThat(productOne).usingRecursiveComparison().isEqualTo(productTwo)
	}
}

class Product() {
	var name: String = ""
	var description: String = ""
	var companyInfo: CompanyInfo? = null
}

class CompanyInfo {
	var name: String = ""
	var address: String = ""
}