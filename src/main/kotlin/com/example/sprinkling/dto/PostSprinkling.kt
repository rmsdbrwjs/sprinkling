package com.example.sprinkling.dto

import javax.validation.constraints.Min

class PostSprinkling {

    class Request(
            @field:Min(value = 1, message = "At least 1")
            val totalAmount: Int,
            @field:Min(value = 1, message = "At least 1")
            val receiverCount: Int)

    class Response(val token: String)

}