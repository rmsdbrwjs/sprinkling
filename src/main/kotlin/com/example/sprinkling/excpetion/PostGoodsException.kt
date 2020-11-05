package com.example.sprinkling.excpetion

import com.example.sprinkling.dto.public.ErrorResponse
import com.example.sprinkling.excpetion.public.ErrorCode
import com.example.sprinkling.excpetion.public.PublicException

class PostGoodsException(message: String) : PublicException(ErrorResponse(ErrorCode.E_102, message))