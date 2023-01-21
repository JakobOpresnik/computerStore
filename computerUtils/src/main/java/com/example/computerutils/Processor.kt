package com.example.computerutils

class Processor(
    private val brand: String,
    private val speed: Double,  // GHz
    private val cores: Int,
    private val threads: Int,
) {
    override fun toString(): String {
        return "- $brand\n" +
                "- $speed GHz\n" +
                "- $cores cores, $threads threads\n"
    }
}