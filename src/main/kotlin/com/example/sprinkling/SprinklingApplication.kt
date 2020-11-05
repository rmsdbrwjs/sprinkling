package com.example.sprinkling

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SprinklingApplication

fun main(args: Array<String>) {
	runApplication<SprinklingApplication>(*args)
}
