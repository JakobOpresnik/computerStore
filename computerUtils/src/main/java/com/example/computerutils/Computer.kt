package com.example.computerutils

import java.util.*

class Computer(
    private val brand: String,
    private val model: String,
    private val screen: Screen,
    private val cpu: Processor,
    private val gpu: GraphicsCard,
    private val memory: Ram,
    private val storage: Storage,
    private val price: Double
): Comparable<Computer> {

    private val id: String = UUID.randomUUID().toString()

    override fun compareTo(other: Computer): Int {
        val price1 = this.price
        val price2 = other.price
        return if (price1 > price2) {
            0
        } else if (price2 > price1) {
            1
        } else -1
    }

    fun getUUID(): String {
        return id.toString()
    }

    fun getBrand(): String {
        return brand
    }

    fun getModel(): String {
        return model
    }

    fun getScreen(): Screen {
        return screen
    }

    fun getCpu(): Processor {
        return cpu
    }

    fun getGpu(): GraphicsCard {
        return gpu
    }

    fun getPrice(): Double {
        return price
    }

    override fun toString(): String {
        return "$brand $model - $price€\n\n" +
                "screen details:\n$screen\n" +
                "CPU details:\n$cpu\n" +
                "GPU details:\n$gpu\n" +
                "RAM details:\n$memory\n" +
                "storage details:\n$storage\n\n"
    }
}