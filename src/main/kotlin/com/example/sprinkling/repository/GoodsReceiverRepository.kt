package com.example.sprinkling.repository

import com.example.sprinkling.entity.GoodsReceiver
import org.springframework.data.jpa.repository.JpaRepository

interface GoodsReceiverRepository : JpaRepository<GoodsReceiver, Long>