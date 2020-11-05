package com.example.sprinkling.service

import com.example.sprinkling.component.GoodsManager
import com.example.sprinkling.component.SprinklingManager
import com.example.sprinkling.dto.GetSprinkling
import com.example.sprinkling.dto.PostGoods
import com.example.sprinkling.dto.PostSprinkling
import com.example.sprinkling.excpetion.GetSprinklingException
import com.example.sprinkling.excpetion.NotFoundRoomOrTokenException
import com.example.sprinkling.excpetion.PostGoodsException
import com.example.sprinkling.excpetion.PostSprinklingException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class SprinklingService(
        val sprinklingManager: SprinklingManager,
        val goodsManager: GoodsManager) {

    @Transactional
    fun postSprinkling(userId: Long, roomId: String,
                       request: PostSprinkling.Request) =
            let {
                val totalAmount = request.totalAmount
                val receiverCount = request.receiverCount

                // Amount receiverCount 보다 커야
                if (totalAmount < receiverCount) {
                    throw PostSprinklingException("TotalAmount cannot be less than receiverCount.")
                }

                // 뿌리기 저장
                val sprinkling = sprinklingManager.saveSprinkling(userId, roomId, totalAmount)

                // 상품 저장
                val sprinklingId = sprinkling.id
                goodsManager.saveGoods(totalAmount, receiverCount, sprinklingId)

                // 응답
                val token = sprinkling.token
                PostSprinkling.Response(token)
            }

    fun postGoods(userId: Long, roomId: String,
                  request: PostGoods.Request) =
            let {
                val token = request.token

                val sprinkling = try {
                    sprinklingManager.getSprinkling(roomId, token)
                } catch (e: NotFoundRoomOrTokenException) {
                    throw PostGoodsException("Rooms or tokens do not match.")
                }

                if (sprinkling.userId == userId) {
                    // 스스로 요청
                    throw PostGoodsException("You can't ask for it yourself.")
                }

                if (sprinkling.recordAt.plusMinutes(10).isBefore(LocalDateTime.now())) {
                    // 10분 지났는지
                    throw PostGoodsException("The valid time is over.")
                }

                if (sprinkling.goodsList.all { it.goodsReceiver != null }) {
                    // 모두 받음
                    throw PostGoodsException("All goods were paid.")
                }

                if (sprinkling.goodsList.any { it.goodsReceiver?.userId == userId }) {
                    // 이미 받음
                    throw PostGoodsException("You have taken it already.")
                }

                // 수령 가능 굿즈 찾기
                val goodsList = sprinkling.goodsList
                val goods = goodsManager.getReceivableGoods(goodsList)

                // 굿즈 수령
                val goodsId = goods.id
                goodsManager.saveGoodsReceiver(goodsId, userId)

                // 응답
                val value = goods.splitValue
                PostGoods.Response(value)
            }

    fun getSprinkling(userId: Long, roomId: String, request: GetSprinkling.Request) = let {
        val token = request.token

        val sprinkling = try {
            sprinklingManager.getSprinkling(roomId, token)
        } catch (e: NotFoundRoomOrTokenException) {
            throw GetSprinklingException("NotFoundRoomOrTokenException")
        }

        // 나 자신 아님
        if (sprinkling.userId != userId) {
            throw GetSprinklingException("It's not yourself.")
        }

        // 7일 경과
        if (sprinkling.recordAt.plusDays(7).isBefore(LocalDateTime.now())) {
            throw GetSprinklingException("The valid time is over.")
        }

        // 응답
        val goodsList = sprinkling.goodsList

        // 받기 완료된 금액
        val goodsReceivedAmount = goodsManager.getGoodsReceivedAmount(goodsList)

        // 받기 완료된 정보 리스트
        val goodsReceiverInfoList = goodsManager.getGoodsReceiverInfoList(goodsList)

        GetSprinkling.Response(
                sprinkling.recordAt,
                sprinkling.totalAmount,
                goodsReceivedAmount,
                goodsReceiverInfoList)
    }

}