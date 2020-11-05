package com.example.sprinkling.component

import com.example.sprinkling.dto.GetSprinkling
import com.example.sprinkling.entity.Goods
import com.example.sprinkling.entity.GoodsReceiver
import com.example.sprinkling.repository.GoodsReceiverRepository
import com.example.sprinkling.repository.GoodsRepository
import org.springframework.stereotype.Component
import kotlin.random.Random
import kotlin.streams.toList

@Component
class GoodsManager(
        val goodsRepository: GoodsRepository,
        val goodsReceiverRepository: GoodsReceiverRepository,
        val numberSpliterator: NumberSpliterator) {

    fun saveGoods(totalAmount: Int, receiverCount: Int, sprinklingId: Long): Unit =
            numberSpliterator
                    .split(totalAmount, receiverCount)
                    .stream()
                    .forEach {
                        saveGoods(it, sprinklingId)
                    }

    fun getReceivableGoods(goodsList: List<Goods>): Goods =
            goodsList
                    .stream()
                    .filter { it.goodsReceiver == null }
                    .toList()
                    .let {
                        it[Random.nextInt(it.size)]
                    }

    fun saveGoodsReceiver(goodsId: Long, userId: Long): GoodsReceiver =
            GoodsReceiver()
                    .apply {
                        this.goodsId = goodsId
                        this.userId = userId
                    }.let {
                        goodsReceiverRepository.save(it)
                    }

    fun getGoodsReceivedAmount(goodsList: List<Goods>): Int =
            goodsList
                    .stream()
                    .filter { it.goodsReceiver != null }
                    .mapToInt { it.splitValue }
                    .sum()

    fun getGoodsReceiverInfoList(goodsList: List<Goods>): List<GetSprinkling.GoodsReceiverInfo> =
            goodsList
                    .stream()
                    .filter { it.goodsReceiver != null }
                    .map { GetSprinkling.GoodsReceiverInfo(it.splitValue, it.goodsReceiver!!.userId) }
                    .toList()

    private fun saveGoods(value: Int, sprinklingId: Long): Goods =
            Goods()
                    .apply {
                        this.splitValue = value
                        this.sprinklingId = sprinklingId
                    }.let {
                        goodsRepository.save(it)
                    }

}