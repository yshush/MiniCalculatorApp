package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)

    }

    fun onDigit(view: View){
        // Toast.makeText(this, "Button clicked", Toast.LENGTH_LONG).show()
        tvInput?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view: View){
        tvInput?.text = ""
    }

    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true

        }
    }

    fun onOperator(view: View){
        tvInput?.text?.let{
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try {
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                // 계산하기
                if(tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")

                    var firstNum = splitValue[0]
                    var secondNum = splitValue[1]

                    if(prefix.isNotEmpty()){
                        firstNum = prefix + firstNum
                    }

                    tvInput?.text = removeZeroAfterDot((firstNum.toDouble() - secondNum.toDouble()).toString())
                }else if(tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")

                    var firstNum = splitValue[0]
                    var secondNum = splitValue[1]

                    if(prefix.isNotEmpty()){
                        firstNum = prefix + firstNum
                    }

                    tvInput?.text = removeZeroAfterDot((firstNum.toDouble() + secondNum.toDouble()).toString())

                }else if(tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")

                    var firstNum = splitValue[0]
                    var secondNum = splitValue[1]

                    if(prefix.isNotEmpty()){
                        firstNum = prefix + firstNum
                    }

                    tvInput?.text = removeZeroAfterDot((firstNum.toDouble() / secondNum.toDouble()).toString())

                } else if(tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")

                    var firstNum = splitValue[0]
                    var secondNum = splitValue[1]

                    if(prefix.isNotEmpty()){
                        firstNum = prefix + firstNum
                    }

                    tvInput?.text = removeZeroAfterDot((firstNum.toDouble() * secondNum.toDouble()).toString())

                }


            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private  fun removeZeroAfterDot(result: String) : String{
        var value = result
        if(result.contains(".0"))
            value = result.substring(0, result.length -2)

        return value
    }

    private fun isOperatorAdded(value : String) : Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }

}