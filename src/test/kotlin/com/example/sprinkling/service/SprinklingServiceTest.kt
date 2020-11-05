package com.example.sprinkling.service

import com.example.sprinkling.dto.PostSprinkling
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(properties = [
    "userId = 100000",
    "roomId = ABCDEF"
])
class SprinklingServiceTest(
        @Autowired val sprinklingService: SprinklingService) {

    @Value("\${userId}")
    private var userId: Long = 0

    @Value("\${roomId}")
    private lateinit var roomId: String

    @Test
    fun postSprinkling() {
        val totalAmount = 105
        val receiverCount = 3

        val request = PostSprinkling.Request(totalAmount, receiverCount)
        val response = sprinklingService.postSprinkling(userId, roomId, request)



    }
}
