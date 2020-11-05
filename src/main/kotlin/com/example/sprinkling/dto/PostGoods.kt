package com.example.sprinkling.dto

import javax.validation.constraints.NotNull

class PostGoods {

    class Request(
            @field:NotNull
            val token: String
    )

    class Response(val value: Int)

}