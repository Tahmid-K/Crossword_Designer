package com.example.crossword_designer.design

import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.crossword_designer.R


var clueAssign : Int = 1
var downButtonPressed : Boolean = false
var acrossButtonPressed : Boolean = false

val cluesDown = mutableListOf<String>()
val cluesAcross = mutableListOf<String>()
var downString : String = ""
var acrossString : String = ""

var downWords : String = ""
var acrossWords : String = ""


class ClueDesignFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clue_design, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var downText : EditText = view.findViewById<EditText>(R.id.down_text)
        var acrossText : EditText = view.findViewById<EditText>(R.id.across_text)
        var numb : TextView? = view?.findViewById(R.id.currentNumber)
        var currentAssignment : TextView = view.findViewById(R.id.current_assignment)
        var downView : TextView = view.findViewById(R.id.down_view)
        var acrossView : TextView = view.findViewById(R.id.across_view)
        currentAssignment.text = "Current Number $clueAssign"
        var downWord : TextView = view.findViewById(R.id.downWord)
        var acrossWord : TextView = view.findViewById(R.id.acrossWord)
        downText.visibility = View.INVISIBLE
        acrossText.visibility = View.INVISIBLE


        val downButtonID: String = "button_down"
        val downID = resources.getIdentifier(downButtonID, "id", activity?.packageName)
        val downButton = view.findViewById<Button>(downID)
        downButton.setOnClickListener {
            downButtonPressed = true
            acrossButtonPressed = false
            downText.visibility = View.VISIBLE
            acrossText.visibility = View.INVISIBLE
            downWords = ""
            acrossWords = ""
            var row = findLocation(clueAssign) / 5
            var location = findLocation(clueAssign)
            var i: Int = location
            for (x in row until 5) {
                println(buttons[i].text.toString())
                downWords += buttons[i].text.toString().last()

                i += 5
            }

            downWord.text = downWords
            acrossWord.text = acrossWords
        }

        val acrossButtonID: String = "button_across"
        val acrossID = resources.getIdentifier(acrossButtonID, "id", activity?.packageName)
        val acrossButton = view.findViewById<Button>(acrossID)
        acrossButton.setOnClickListener {
            acrossButtonPressed = true
            downButtonPressed = false
            downText.visibility = View.INVISIBLE
            acrossText.visibility = View.VISIBLE

            downWords = ""
            acrossWords = ""
            var col = findLocation(clueAssign) % 5
            var location  = findLocation(clueAssign)
            var i : Int = location
            for(x in col until 5){
                acrossWords+=buttons[i].text.toString().last()
                i+=1
            }


            downWord.text = downWords
            acrossWord.text = acrossWords



        }

        val bothButtonID: String = "button_both"
        val bothID = resources.getIdentifier(bothButtonID, "id", activity?.packageName)
        val bothButton = view.findViewById<Button>(bothID)
        bothButton.setOnClickListener {
            acrossButtonPressed = true
            downButtonPressed = true
            downText.visibility = View.VISIBLE
            acrossText.visibility = View.VISIBLE

            downWords = ""
            acrossWords = ""

            var row = findLocation(clueAssign) / 5
            var col = findLocation(clueAssign) % 5
            var location = findLocation(clueAssign)

            var i : Int = location
            for(x in col until 5){
                downWords+=buttons[i].text.toString().last()
                i+=5
            }

            i = location
            for(x in row until 5){
                acrossWords+=buttons[i].text.toString().last()
                i+=1
            }

            downWord.text = downWords
            acrossWord.text = acrossWords

        }




        val nextClueButtonID: String = "button_next_clue"
        val nextClueID = resources.getIdentifier(nextClueButtonID, "id", activity?.packageName)
        val nextClueButton = view.findViewById<Button>(nextClueID)
        nextClueButton.setOnClickListener {
            if(acrossButtonPressed && downButtonPressed){
                println("Both are found")
                downString = "Down \n"
                acrossString = "Across \n"
                if(acrossText.text.toString() != "" && downText.text.toString() != ""){
                    var acrossStr : String = acrossText.text.toString()
                    var downStr : String = downText.text.toString()
                    cluesAcross.add("$clueAssign $acrossStr")
                    cluesDown.add("$clueAssign $downStr")
                    for(i in cluesAcross) {
                        acrossString += "$i \n"
                    }
                    for(i in cluesDown){
                    downString+= "$i \n"
                    }
                    acrossView.text = acrossString
                    downView.text = downString
                    clueAssign+=1
                    currentAssignment.text = "Current Number $clueAssign"
                    downText.text.clear()
                    acrossText.text.clear()
                    println("button finished")
                }
            }



            else if(acrossButtonPressed){
                println("across is found")
                acrossString = "Across\n"
                if(acrossText.text.toString() != ""){
                    var acrossStr : String = acrossText.text.toString()
                    cluesAcross.add("$clueAssign $acrossStr")
                    for(i in cluesAcross){
                        acrossString+= "$i \n"
                    }
                    acrossView.text = acrossString
                    clueAssign+=1
                    currentAssignment.text = "Current Number $clueAssign"
                    downText.text.clear()
                    acrossText.text.clear()
                }
            }

            else if(downButtonPressed){
                println("down is found")
                downString = "Down"
                if(downText.text.toString() != ""){
                    var downStr : String = downText.text.toString()
                    cluesDown.add("$clueAssign $downStr")
                    for(i in cluesDown){
                    downString+= "$i \n"
                    }
                    downView.text = downString
                    clueAssign+=1
                    currentAssignment.text = "Current Number $clueAssign"
                    downText.text.clear()
                    acrossText.text.clear()
                }
            }
        }
    }
}


fun findLocation(num: Int): Int {
    var loc : Int = 0
    for (i in 0 until buttons.size) {
        if (num == buttons[i].text[0].toString().toInt())
        {
            loc = i
            break
        }
    }
    return loc
}