package com.example.sprinkling.entity

import javax.persistence.*

@Entity
@Table
class Goods(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,
        var splitValue: Int = 0,
        var sprinklingId: Long = 0,
        @OneToOne
        @JoinColumn(name = "id")
        var goodsReceiver: GoodsReceiver? = null
)
