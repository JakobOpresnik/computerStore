package com.example.computerutils

class ComputersList {
    var listofComputers: MutableList<Computer> = ArrayList()

    fun addComputer(computer: Computer) {
        listofComputers.add(computer)
    }

    fun updateAtIndex(index: Int, updatedComputer: Computer) {
        listofComputers[index] = updatedComputer
    }
}