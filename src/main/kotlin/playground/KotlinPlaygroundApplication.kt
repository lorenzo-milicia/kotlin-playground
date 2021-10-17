package playground

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.runApplication

@SpringBootConfiguration
class KotlinPlaygroundApplication

fun main(args: Array<String>) {
	runApplication<KotlinPlaygroundApplication>(*args)
}