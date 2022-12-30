package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var lastNumeric = false
    var lastDot = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var input = findViewById<TextView>(R.id.textView)
    }
    fun onDigit(view: View){
        var input = findViewById<TextView>(R.id.textView)
        input.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View){
        var input = findViewById<TextView>(R.id.textView)
        input.text = ""
        lastNumeric = false
        lastDot = false
    }
    fun onDecimalPoint(view: View) {
        var input = findViewById<TextView>(R.id.textView)
        if (lastNumeric && !lastDot) {
            input.append(".")
            lastNumeric = false
            lastDot = true
        }
    }
    fun onOperator(view: View) {
        var input = findViewById<TextView>(R.id.textView)
        input.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                input.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }

    }
    fun onEqual(view: View) {

        if (lastNumeric) {
            var input = findViewById<TextView>(R.id.textView)
            var tvValue = input.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1);
                }

                when {
                    tvValue.contains("/") -> {

                        val splitedValue = tvValue.split("/")

                        var one = splitedValue[0]
                        val two = splitedValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        input.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                    }
                    tvValue.contains("X") -> {

                        val splitedValue = tvValue.split("X")

                        var one = splitedValue[0]
                        val two = splitedValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }

                        input.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                    }
                    tvValue.contains("-") -> {
                        val splitedValue = tvValue.split("-")

                        var one = splitedValue[0]
                        val two = splitedValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }


                        input.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                    }
                    tvValue.contains("+") -> {

                        val splitedValue = tvValue.split("+")

                        var one = splitedValue[0]
                        val two = splitedValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }


                        input.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                    }
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String {

        var value = result

        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }

        return value
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            (value.contains("/")
                    || value.contains("*")
                    || value.contains("-")
                    || value.contains("+"))
        }
    }
    fun back(view: View){
        var input = findViewById<TextView>(R.id.textView)
        if(input.text.isNotEmpty()){
        input.text = input.text.toString().substring(0, input.text.length - 1  )}
        else {
            Toast.makeText(this,"no input", Toast.LENGTH_SHORT).show()
        }

    }
}