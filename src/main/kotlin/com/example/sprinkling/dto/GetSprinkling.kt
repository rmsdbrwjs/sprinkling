package com.example.sprinkling.dto

import java.time.LocalDateTime
import javax.validation.constraints.NotNull

class GetSprinkling {

    class Request(
            @field:NotNull
            val token: String
    )

    class Response(
            val sprinklingRecordAt: LocalDateTime,
            val totalAmount: Int,
            val goodsReceivedAmount: Int,
            val goodsReceiverInfoList: List<GoodsReceiverInfo>) {
    }

    class GoodsReceiverInfo(
            val value: Int,
            val userId: Long
    )

}