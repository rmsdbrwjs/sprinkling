package com.example.sprinkling.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(
        uniqueConstraints = [
            UniqueConstraint(columnNames = ["roomId", "token"])
        ]
)
class Sprinkling(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,
        var userId: Long = 0,
        var roomId: String = "",
        var token: String = "",
        var totalAmount: Int = 0,
        var recordAt: LocalDateTime = LocalDateTime.now(),
        @OneToMany
        @JoinColumn(name = "sprinklingId")
        var goodsList: List<Goods> = ArrayList()

)
