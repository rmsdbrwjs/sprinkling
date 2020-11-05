package com.example.sprinkling.controller

import com.example.sprinkling.dto.GetSprinkling
import com.example.sprinkling.dto.PostGoods
import com.example.sprinkling.dto.PostSprinkling
import com.example.sprinkling.excpetion.GetSprinklingException
import com.example.sprinkling.excpetion.PostGoodsException
import com.example.sprinkling.excpetion.PostSprinklingException
import com.example.sprinkling.excpetion.public.PublicException
import com.example.sprinkling.service.SprinklingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping("/sprinkling")
@RestController
class SprinklingController(
        val sprinklingService: SprinklingService) {

    @PostMapping
    fun postSprinkling(
            @RequestHeader("X-USER-ID") userId: Long,
            @RequestHeader("X-ROOM-ID") roomId: String,
            @RequestBody @Valid request: PostSprinkling.Request
    ) = sprinklingService.postSprinkling(userId, roomId, request)

    @PostMapping("/goods")
    fun postGoods(@RequestHeader("X-USER-ID") userId: Long,
                  @RequestHeader("X-ROOM-ID") roomId: String,
                  @RequestBody @Valid request: PostGoods.Request
    ) = sprinklingService.postGoods(userId, roomId, request)

    @GetMapping
    fun getSprinkling(
            @RequestHeader("X-USER-ID") userId: Long,
            @RequestHeader("X-ROOM-ID") roomId: String,
            @RequestBody @Valid request: GetSprinkling.Request
    ) = sprinklingService.getSprinkling(userId, roomId, request)

    @ExceptionHandler(value = [PostGoodsException::class, PostSprinklingException::class, GetSprinklingException::class])
    fun handlePostGoodsException(e: PublicException) =
            ResponseEntity
                    .badRequest()
                    .body(e.errorResponse)

}