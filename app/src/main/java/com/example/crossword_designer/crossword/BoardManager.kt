package com.example.crossword_designer.crossword

data class CellInput(val position: Position, val entries: Pair<Entry?, Entry?>? = null, val letter: Char? = null,
                     val active: Boolean? = null, val number: Int? = null)

class BoardManager(val clues: Clues, private val aBoard: Board = Board(mutableListOf<Cell>())) {

    public fun inputCell(anInput: CellInput) : Boolean {
        return aBoard.editCell(anInput)
    }

    public fun inputLetter(aPosition: Position, aLetter: Char) : Boolean {
        return aBoard.editCell(CellInput(aPosition, letter = aLetter))
    }

    public fun inputActive(aPosition: Position, isActive: Boolean) : Boolean {
        return aBoard.editCell(CellInput(aPosition, active = isActive))
    }

    public fun inputNumber(aPosition: Position, aNumber: Int) : Boolean {
        return aBoard.editCell(CellInput(aPosition, number = aNumber))
    }

    public fun getCells() : List<Cell> {
        return aBoard.getCells()
    }
}

data class Clues(val number: Int, val horizontal: Boolean, val clue: MutableList<String>)