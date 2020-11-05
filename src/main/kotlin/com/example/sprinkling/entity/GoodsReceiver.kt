package com.example.sprinkling.entity

import javax.persistence.*

@Entity
@Table
class GoodsReceiver(
        @Id
        var goodsId: Long = 0,
        var userId: Long = 0
)