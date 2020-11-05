package com.example.sprinkling.repository

import com.example.sprinkling.entity.Sprinkling
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SprinklingRepository : JpaRepository<Sprinkling, Long> {

    fun findByRoomIdAndToken(roomId: String, token: String): Optional<Sprinkling>

}