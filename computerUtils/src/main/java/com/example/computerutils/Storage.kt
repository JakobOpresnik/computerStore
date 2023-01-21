package com.example.computerutils

class Storage(
    private val type: StorageType,
    private val capacity: Int
) {
    override fun toString(): String {
        return "- ${capacity} GB $type\n"
    }
}