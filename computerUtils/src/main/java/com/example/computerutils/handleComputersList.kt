package com.example.computerutils

import java.util.UUID

/**
 * @param n amount of generated computers
 * @return list of computers with random specifications
 */
fun generateComputers(n: Int): ComputersList {

    val computers = ComputersList()

    // different brands & models
    val brand1 = "Lenovo"
    val model1 = "Legion"
    val brand2 = "Asus"
    val model2 = "ROG Strix"
    val brand3 = "Razer"
    val model3 = "Blade"
    val brands = listOf("$brand1 $model1", "$brand2 $model2", "$brand3 $model3")

    // differents screens
    val screen1 = Screen(1920, 1080, 120, PanelType.IPS)
    val screen2 = Screen(1600, 900, 60, PanelType.TN)
    val screen3 = Screen(3840, 2160, 144, PanelType.IPS)
    val screens = listOf(screen1, screen2, screen3)

    // different CPUs
    val cpu1 = Processor("AMD", 3.5, 6, 12)
    val cpu2 = Processor("Intel", 3.7, 4, 8)
    val cpu3 = Processor("Intel", 4.2, 8, 16)
    val processors = listOf(cpu1, cpu2, cpu3)

    // different GPUs
    val gpu1 = GraphicsCard("Nvidia 1650", 900, 1492)
    val gpu2 = GraphicsCard("Nvidia 3090 Ti", 1600, 2000)
    val gpu3 = GraphicsCard("AMD RX 6900 XT", 1500, 1875)
    val graphicsCards = listOf(gpu1, gpu2, gpu3)

    // different RAM
    val mem1 = Ram(8, 2)
    val mem2 = Ram(8, 1)
    val mem3 = Ram(16, 2)
    val memory = listOf(mem1, mem2, mem3)

    // different disks
    val disk1 = Storage(StorageType.SSD, 500)
    val disk2 = Storage(StorageType.HDD, 2000)
    val disk3 = Storage(StorageType.SSD, 1000)
    val disks = listOf(disk1, disk2, disk3)

    // different prices
    val prices = listOf(549.99, 849.99, 1199.99, 1699.99)

    for (i in 1..n) {
        // generate random specifications
        //val id = UUID.randomUUID().toString()
        val name = brands.random()
        val brand = name.split(" ")[0]
        val model = name.split(" ")[1]
        val screen = screens.random()
        val cpu = processors.random()
        val gpu = graphicsCards.random()
        val ram = memory.random()
        val storage = disks.random()
        val price = prices.random()
        val computer = Computer(brand, model, screen, cpu, gpu, ram, storage, price)
        // add new computer to the list
        computers.addComputer(computer)
    }

    return computers
}

/**
 * @param computers sorted ascending by price
 * @return sorted list
 */
fun sort(computers: MutableList<Computer>): List<Computer> {
    return computers.sortedWith(compareBy{ it.getPrice() })
}