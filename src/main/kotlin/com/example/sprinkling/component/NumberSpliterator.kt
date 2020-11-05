package com.example.sprinkling.component

import com.example.sprinkling.excpetion.SplitFailedException
import org.springframework.stereotype.Component
import java.util.stream.IntStream
import kotlin.random.Random
import kotlin.streams.toList

@Component
class NumberSpliterator {

    @Throws(SplitFailedException::class)
    fun split(dividend: Int, divisor: Int) =
            getSplitList(dividend, divisor)
                    .apply {
                        if (divisor != this.size) {
                            throw SplitFailedException("Divisor size error")
                        }

                        if (dividend != getTotal(this)) {
                            throw SplitFailedException("Dividend value error")
                        }
                    }


    private fun getSplitList(dividend: Int, divisor: Int) = run {
        var remain = dividend
        val splitList: MutableList<Int> = IntStream
                .range(1, divisor)
                .map {
                    val half = remain / 2
                    val quotient = Random.nextInt(half)
                    remain -= quotient
                    quotient
                }
                .toList()
                .toMutableList()

        splitList.add(remain)

        splitList
    }

    private fun getTotal(list: List<Int>) = list
            .let {
                var totalSplit = 0
                for (split in it) {
                    totalSplit += split
                }
                totalSplit
            }

}