package com.example.interviewpractice.types

class UserException(message:String): Exception(message)
class SystemException(message:String): Exception(message)

class CatastrophicException(message:String): Exception(message)

class UIError(val message: String, val errorType: ErrorType )

enum class ErrorType {
    USER, SYSTEM, CATASTROPHIC
}