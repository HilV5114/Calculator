package com.example.calculatorapp

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

/**
 * A button that measures itself as a square: width == height.
 */
class SquareButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatButton(context, attrs, defStyle) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Force the height measure to match width
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}
