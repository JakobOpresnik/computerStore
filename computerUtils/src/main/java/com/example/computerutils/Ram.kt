package com.example.computerutils

class Ram(
    private val capacity: Int,
    private val units: Int,
) {
    override fun toString(): String {
        return "- ${units}x${capacity}GB\n"
    }
}