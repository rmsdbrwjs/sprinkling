package com.example.sprinkling.excpetion.public

import com.example.sprinkling.dto.public.ErrorResponse

open class PublicException(val errorResponse: ErrorResponse) : RuntimeException(errorResponse.message)