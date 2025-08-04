package com.example.calculatorapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvExpression: TextView
    private lateinit var tvResult: TextView
    private var expression: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize TextViews
        tvExpression = findViewById(R.id.tvExpression)
        tvResult = findViewById(R.id.tvResult)

        tvExpression.text = ""
        tvResult.text = "0"
    }

    // Handle number button clicks
    fun onNumber(view: View) {
        val btn = view as Button
        expression += btn.text.toString()
        tvExpression.text = expression
    }

    // Handle decimal button
    fun onDecimal(view: View) {
        if (expression.isEmpty() || expression.last() !in '0'..'9') {
            expression += "0."
        } else if (!expression.endsWith(".")) {
            expression += "."
        }
        tvExpression.text = expression
    }

    // Handle operator button (+, –, ×, ÷)
    fun onOperator(view: View) {
        val btn = view as Button
        val op = btn.text.toString()
        if (expression.isNotEmpty() && expression.last() !in "+–×÷") {
            expression += op
            tvExpression.text = expression
        }
    }

    // Handle equals button (=)
    fun onEqual(view: View) {
        val resultValue = calculateExpression(expression)
        tvResult.text = formatResult(resultValue)
    }

    // Handle clear button (AC)
    fun onClear(view: View) {
        expression = ""
        tvExpression.text = ""
        tvResult.text = "0"
    }

    // Handle backspace (⌫)
    fun onDelete(view: View) {
        if (expression.isNotEmpty()) {
            expression = expression.dropLast(1)
            tvExpression.text = expression
        }
    }

    // Handle percentage (%)
    fun onPercent(view: View) {
        val resultValue = calculateExpression("($expression)/100")
        tvResult.text = formatResult(resultValue)
    }

    // Format so integers don’t get “.0”
    private fun formatResult(value: Double): String {
        return when {
            value.isNaN()      -> "Error"
            value % 1.0 == 0.0 -> value.toLong().toString()
            else               -> value.toString()
        }
    }

    /**
     * Simple math expression evaluator
     * Supports: +, -, ×, ÷
     */
    private fun calculateExpression(expr: String): Double {
        return try {
            if (expr.isEmpty()) return Double.NaN
            val safe = expr
                .replace("×", "*")
                .replace("÷", "/")
                .replace("–", "-")
            val tokens = safe.split("(?<=[-+*/])|(?=[-+*/])".toRegex()).toMutableList()

            var result = tokens[0].toDouble()
            var i = 1
            while (i < tokens.size) {
                val op = tokens[i]
                val next = tokens[i + 1].toDouble()
                result = when (op) {
                    "+" -> result + next
                    "-" -> result - next
                    "*" -> result * next
                    "/" -> result / next
                    else -> next
                }
                i += 2
            }
            result
        } catch (e: Exception) {
            Double.NaN
        }
    }
}
