package com.example.sprinkling.component

import com.example.sprinkling.entity.Sprinkling
import com.example.sprinkling.excpetion.NotFoundRoomOrTokenException
import com.example.sprinkling.repository.SprinklingRepository
import org.hibernate.exception.ConstraintViolationException
import org.springframework.stereotype.Component

@Component
class SprinklingManager(
        val sprinklingRepository: SprinklingRepository,
        val textGenerator: TextGenerator) {

    fun saveSprinkling(userId: Long, roomId: String, totalAmount: Int): Sprinkling =
            Sprinkling()
                    .apply {
                        this.userId = userId
                        this.roomId = roomId
                        this.token = textGenerator.generate()
                        this.totalAmount = totalAmount
                    }.let {
                        saveSprinkling(it)
                    }

    fun getSprinkling(roomId: String, token: String): Sprinkling =
            sprinklingRepository
                    .findByRoomIdAndToken(roomId, token)
                    .orElseThrow { NotFoundRoomOrTokenException() }

    private fun saveSprinkling(sprinkling: Sprinkling): Sprinkling =
            sprinkling
                    .apply {
                        this.token = textGenerator.generate()
                    }.let {
                        try {
                            sprinklingRepository.save(it)
                        } catch (e: ConstraintViolationException) {
                            return saveSprinkling(it)
                        }
                    }

}