package com.example.crossword_designer.design

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.crossword_designer.R
import com.example.crossword_designer.crossword.Board
import com.example.crossword_designer.crossword.Cell



val COLUMNS = 5
val ROWS = 5
val buttons = mutableListOf<Button>()
val cellList = mutableListOf<Cell>()
var numberAssign : Int = 1


var assignButton : Boolean = true
var assignNumbers : Boolean = false
var assignLetters : Boolean = false
var assignClues : Boolean = false
lateinit var input: EditText
lateinit var instruct : TextView

var board = Board(cellList)

class DesignFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_design, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("Starting app")
        var input : EditText = view.findViewById<EditText>(R.id.addText)
        input.visibility = View.GONE

        var instruct : TextView = view.findViewById<TextView>(R.id.instructions)

        var count: Int = 0
        for (i in 0 until COLUMNS) {
            for (j in 0 until ROWS) {
                val buttonID: String = "button_$i$j"
                val resID = resources.getIdentifier(buttonID, "id", activity?.packageName)
                buttons.add(view.findViewById(resID))
                buttons[count].setOnClickListener(this)
                count += 1
            }
        }
        val rstButtonID: String = "button_reset"
        val rstID = resources.getIdentifier(rstButtonID, "id", activity?.packageName)
        val rstButton = view.findViewById<Button>(rstID)
        rstButton.setOnClickListener {
            if (assignButton) {
                for (i in 0 until buttons.size) {
                    buttons[i].text = ""
                    buttons[i].setBackgroundResource(R.drawable.button_border)
                }
            }
            if (assignNumbers) {
                for (i in 0 until buttons.size) {
                    if (buttons[i].text != "$") {
                        buttons[i].text = ""
                        //buttons[i].setBackgroundResource(R.drawable.button_border)
                    }
                    numberAssign = 1

                }
            }

            if(assignLetters){
                for (i in 0 until buttons.size) {

                    if (buttons[i].text != "") {
                        var firstIndex: String = buttons[i].text[0].toString()
                        if (firstIndex == " ") {
                            buttons[i].text = ""
                        } else {
                            buttons[i].text = firstIndex
                        }
                    }
                }
            }
        }


        val nextButtonID: String = "button_next"
        val nextID = resources.getIdentifier(nextButtonID, "id", activity?.packageName)
        val nextButton = view.findViewById<Button>(nextID)
        nextButton.setOnClickListener {
            if (assignButton) {
                var count: Int = 1
                for (i in 0 until buttons.size) {
                    if (buttons[i].text == "$") {
                        buttons[i].visibility = View.INVISIBLE
                    }
                    assignButton = false
                    assignNumbers = true
                    instruct.text = "Add Numbers"

                }
            }

            else if (assignNumbers){

                assignNumbers = false
                assignLetters = true
                input.visibility = View.VISIBLE
                instruct.text = "Add words"


            }

            else if(assignLetters){
                assignLetters = false
                assignClues = true
                //setContentView(R.layout.clues)
                var navController = Navigation.findNavController(view)
                navController!!.navigate(R.id.action_designFragment_to_ClueDesignFragment)
                //action_mainFragment_to_ClueDesignFragment

            }


        }


        val backButtonID: String = "button_back"
        val backID = resources.getIdentifier(backButtonID, "id", activity?.packageName)
        val backButton = view.findViewById<Button>(backID)
        backButton.setOnClickListener {
            if (assignNumbers) {
                for (i in 0 until buttons.size) {
                    if (buttons[i].text != "" && buttons[i].text != "$") {
                        buttons[i].text = ""

                    }
                    if(buttons[i].visibility == View.INVISIBLE){
                        buttons[i].visibility = View.VISIBLE

                    }
                }
                assignButton = true
                assignNumbers = false
                numberAssign = 1
                instruct.text = "Design Layout"

            }

            if (assignLetters){

                for (i in 0 until buttons.size) {

                    if (buttons[i].text != "") {
                        var firstIndex: String = buttons[i].text[0].toString()
                        if (firstIndex == " ") {
                            buttons[i].text = ""
                        } else {
                            buttons[i].text = firstIndex
                        }
                    }
                }


                assignNumbers = true
                assignLetters = false
                instruct.text = "Add Numbers"
                input.visibility = View.GONE
            }

            else if(assignClues){
                assignLetters = true
                assignClues = false
            }

        }

        val recommendButtonID: String = "button_recommend"
        val recommendID = resources.getIdentifier(recommendButtonID, "id", activity?.packageName)
        val recommendButton = view.findViewById<Button>(recommendID)
        recommendButton.setOnClickListener {
            if (assignLetters) {


            }
        }

        /*if(assignClues){

            var downInput : EditText = findViewById<EditText>(R.id.down_text) var acrossInput : EditText = findViewById<EditText>(R.id.across_text)
            var nextButton : Button = findViewById(R.id.button_next_clue)
            var prevButton : Button = findViewById(R.id.button_prev)
            var currNumb : TextView = findViewById(R.id.current_assignment)
            downInput.visibility = View.GONE
            acrossInput.visibility = View.GONE
            nextButton.visibility = View.GONE
            prevButton.visibility = View.GONE
            currNumb.text = "Current Number: $clueAssign"

            val downButtonID: String = "button_down"
            val downID = resources.getIdentifier(downButtonID, "id", packageName)
            val downButton = view.findViewById<Button>(downID)
            downButton.setOnClickListener {
                var downInput : EditText = view.findViewById<EditText>(R.id.down_text)
                var nextButton : Button = view.findViewById(R.id.button_next_clue)
                downInput.visibility = View.VISIBLE
                nextButton.visibility = View.VISIBLE
           }
        }*/
    }
    override fun onClick(v: View?) {
        if (assignButton) {
            val changeButton: Button = v as Button
            val row = changeButton.toString()[changeButton.toString().length - 3]
            val col = changeButton.toString()[changeButton.toString().length - 2]
            println("($row,$col)")

            if (changeButton.text == "") {
                println("Button is GONE")
                changeButton.setBackgroundColor(Color.BLACK)
                changeButton.text = "$"
            } else {
                println("Button is VISIBLE")
                changeButton.setBackgroundResource(R.drawable.button_border)
                changeButton.text = ""
            }
        }

        if(assignNumbers){
            val assignNumberToButton : Button = v as Button
            if(assignNumberToButton.text == ""){
                assignNumberToButton.text = numberAssign.toString()
                numberAssign +=1
            }

            else{

                var currNum : Int = assignNumberToButton.text.toString().toInt()
                assignNumberToButton.text = ""

                for (i in currNum until numberAssign) {
                    var curr : String = (i+1).toString()
                    for ( j in 0 until buttons.size) {
                        println("$curr == " + buttons[j].text.toString())
                        if(curr == buttons[j].text.toString()){
                            buttons[j].text = i.toString()
                            break
                        }
                    }
                }
                numberAssign -=1
            }

            var numb : TextView? = view?.findViewById(R.id.currentNumber)
            if (numb != null) {
                numb.text = numberAssign.toString()
            }
        }

        if (assignLetters){
            println("Start Assigning Letters")
            val assignLetterToButton : Button = v as Button
            //val view = view.inflate(R.layout.fragment_design,false)
            var input : EditText? = view?.findViewById(R.id.addText)
            println("Found editText Letter")
            if (input != null) {
                if(input.text.toString() != ""){
                    if(assignLetterToButton.text.toString() != "" && assignLetterToButton.text.toString()[0] != ' '){
                        var gettingNumber: Char = assignLetterToButton.text.toString()[0]
                        var addingLetter : String = gettingNumber+"   ${input.text.toString()}"
                        assignLetterToButton.text = addingLetter
                    } else{

                        var addingLetter : String = "      ${input.text.toString()}"
                        assignLetterToButton.text = addingLetter

                    }
                }
            }
        }


    }


}

fun addFragment(
    fm: FragmentManager,
    fragment: Fragment,
    container: Int,
    replace: Boolean,
    addToBackStack: Boolean,
    addAnimation: Boolean
) {
    val fragmentTransaction = fm.beginTransaction()
    if (addAnimation)
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in_right,
            R.anim.slide_out_left,
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )
    if (replace)
        fragmentTransaction.replace(container, fragment, fragment.javaClass.name)
    else
        fragmentTransaction.add(container, fragment, fragment.javaClass.name)
    if (addToBackStack)
        fragmentTransaction.addToBackStack(fragment.javaClass.name)
    fragmentTransaction.commit()
}