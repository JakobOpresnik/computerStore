package com.example.computerutils

class GraphicsCard(
    private val model: String,
    private val cores: Int,
    private val speed: Int  // MHz
) {
    override fun toString(): String {
        return "- $model\n" +
                "- $cores CUDA cores\n" +
                "- $speed MHz\n"
    }
}