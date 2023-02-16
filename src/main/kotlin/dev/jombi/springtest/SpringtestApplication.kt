package dev.jombi.springtest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringtestApplication

fun main(args: Array<String>) {
    runApplication<SpringtestApplication>(*args)
}
