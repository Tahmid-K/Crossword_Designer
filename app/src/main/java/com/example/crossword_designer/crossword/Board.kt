package com.example.crossword_designer.crossword

class Board(private val cellList: MutableList<Cell>) {
    private val xDimension = 5
    private val yDimension = 5
    private val dimension = xDimension * yDimension

    init {
        cellList
        for( i in 0 until dimension) {
            val x = i%xDimension
            val y = i/xDimension
            cellList.add(i, Cell(Position(x,y), null))
        }
    }
    public fun setLayout(): Boolean {
        for (aCell in cellList) {
            if (aCell.active && aCell.number != 0) {
                // cell holds a number
                val theHorizontalEntry = createHorizontalEntry(aCell.position, cellList)
                val theVerticalEntry = createVerticalEntry(aCell.position, cellList)
                aCell.entries = Pair(theHorizontalEntry, theVerticalEntry)
                if(theVerticalEntry == null && theHorizontalEntry == null) {
                    return false;
                }
            }
        }
        return true
    }

    public fun editCell(anInput: CellInput) : Boolean {
        val x = anInput.position.x
        val y = anInput.position.y
        val coordinate = x + y*xDimension
        val theCell = cellList[coordinate]
        theCell.active = anInput.active?:theCell.active
        theCell.letter = anInput.letter?:theCell.letter
        theCell.number = anInput.number?:theCell.number
        cellList[coordinate] = theCell
        return true
    }

    public fun getCells() : List<Cell> {
        return cellList
    }

    private fun createVerticalEntry(aPosition: Position, cellList: List<Cell>): Entry? {
        val aCoordinate = aPosition.x + xDimension * aPosition.y
        if (aPosition.y == 0 || !cellList[aCoordinate - xDimension].active) {
            // cell is a top most entry
            var anEntry = Entry(aPosition, false)
            var aString = ""
            for (i in aPosition.y until yDimension) {
                val aCell = cellList[aPosition.x + xDimension * i]
                if (aCell.active) {
                    if (aCell.letter != '-') {
                        aString += aCell.letter
                    }
                }
                else if(aString.length > 1) {
                    anEntry.clue = aString
                    return anEntry
                }
                else {
                    return null
                }
            }
        }
        return null
    }

    private fun createHorizontalEntry(aPosition: Position, cellList: List<Cell>): Entry? {
        val aCoordinate = aPosition.x + xDimension * aPosition.y
        if (aPosition.x == 0 || !cellList[aCoordinate - 1].active) {
            // cell is a left most entry
            var anEntry = Entry(aPosition, true)
            var aString = ""
            for (i in aPosition.x until xDimension) {
                val aCell = cellList[aPosition.x + xDimension * i]
                if (aCell.active) {
                    if (aCell.letter != '-') {
                        aString += aCell.letter
                    }
                }
                else if(aString.length > 1) {
                    anEntry.clue = aString
                    return anEntry
                }
                else {
                    return null
                }
            }
        }
        return null
    }
}


data class Entry(var position: Position, var horizontal: Boolean, var word: String = "", var clue: String = "")

// <horizontal, vertical>
data class Cell(val position: Position, var entries: Pair<Entry?, Entry?>?, var letter: Char = '-', var active: Boolean = false, var number: Int = 0)

data class Position(var x: Int, var y: Int)
