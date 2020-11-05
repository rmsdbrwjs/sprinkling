package com.example.sprinkling.component

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class NumberSpliteratorTest(
        @Autowired val numberSpliterator: NumberSpliterator) {

    @Test
    fun split() {
        val dividend = 36
        val divisor = 6

        numberSpliterator
                .split(dividend, divisor)
                .also {

                    assertThat(it.size, Matchers.`is`(divisor))
                    assertThat(it.stream().mapToInt { v -> v }.sum(), Matchers.`is`(dividend))
                }
    }

}