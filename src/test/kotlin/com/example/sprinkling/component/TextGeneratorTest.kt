package com.example.sprinkling.component

import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TextGeneratorTest(
        @Autowired val textGenerator: TextGenerator) {

    @Test
    fun generate() {
        val length = 3
        textGenerator
                .generate(length)
                .also {
                    MatcherAssert.assertThat(it.length, Matchers.`is`(length))
                }
    }

}