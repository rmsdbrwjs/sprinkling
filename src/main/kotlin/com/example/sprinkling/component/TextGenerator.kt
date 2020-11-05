package com.example.sprinkling.component

import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class TextGenerator {

    fun generate(length: Int = 3) =
            StringBuilder()
                    .let {
                        for (idx in 0 until length) {
                            it.append(getRandomChar())
                        }
                        it.toString()
                    }

    private fun getRandomChar(number: Int = LocalDateTime.now().second) =
            number
                    .let { (Random().nextInt(it) % 26) + 97 }
                    .let {
                        if (Random().nextInt(2) % 2 == 0)
                            it.toChar()
                        else {
                            it.toChar().toUpperCase()
                        }
                    }

}

