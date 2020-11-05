package com.example.sprinkling.dto.public

import com.example.sprinkling.excpetion.public.ErrorCode

class ErrorResponse(val errorCode: ErrorCode, val message: String)