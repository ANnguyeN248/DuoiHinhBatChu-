package com.example.duoihinhbatchu.domain

data class Button(
    var body: String = ""
)

enum class ButtonType {
    BASIC, CORRECT, INCORRECT
}
