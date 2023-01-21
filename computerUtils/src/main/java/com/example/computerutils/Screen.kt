package com.example.computerutils

class Screen(
    private val width: Int,
    private val height: Int,
    private val refreshRate: Int,
    private val panel: PanelType,
) {
    override fun toString(): String {
        return "- ${width}x${height}p\n" +
                "- $refreshRate Hz\n" +
                "- $panel panel\n"
    }
}