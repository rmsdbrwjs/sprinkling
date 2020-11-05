package com.example.sprinkling.repository

import com.example.sprinkling.entity.Goods
import org.springframework.data.jpa.repository.JpaRepository

interface GoodsRepository : JpaRepository<Goods, Long> {

    fun findBySprinklingId(sprinklingId: Long): List<Goods>

}