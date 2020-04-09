package com.example.template.ui.common.view

import android.text.Editable
import android.text.TextWatcher
import android.text.method.DigitsKeyListener

class MaskTextWatcher(
    editText: NormalEditText,
    var isDecimal: Boolean,
    var formatter: (String?) -> String?
) : TextWatcher {

    private var editText: NormalEditText? = editText
    private var beforeText: String = ""


    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        beforeText = s.toString()
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        // 1. get cursor position : s = start + before
        val initialCursorPosition = start + before
        //2. get digit count after cursor position : c0
        val numOfDigitsToRightOfCursor = getNumberOfDigits(
            beforeText.substring(initialCursorPosition, beforeText.length)
        )

        // cleanString this the string which not contain prefix and ,
        val re = Regex("[^0-9.]")//regex allow
        val cleanString = re.replace(s.toString(), "")

        if (cleanString.isEmpty()) return
        if (isDecimal) {
            if (cleanString.contains('.')) {
                editText?.keyListener = DigitsKeyListener.getInstance("0123456789")
            } else {
                editText?.keyListener = DigitsKeyListener.getInstance("0123456789.")
            }
        }

        val newFormat = formatter.invoke(cleanString)
        editText?.removeTextChangedListener(this)
        editText?.setText(newFormat)

        //set new cursor position
        editText?.setSelection(getNewCursorPosition(numOfDigitsToRightOfCursor, newFormat ?: ""))
        editText?.addTextChangedListener(this)
    }

    override fun afterTextChanged(s: Editable) {
        //blank
    }

    private fun getNewCursorPosition(
        digitCountToRightOfCursor: Int,
        numberString: String
    ): Int {
        var position = 0
        var c = digitCountToRightOfCursor
        for (i in numberString.reversed()) {
            if (c == 0)
                break

            if (i.isDigit())
                c--
            position++


        }
        return numberString.length - position
    }

    private fun getNumberOfDigits(text: String): Int {
        var count = 0
        for (i in text)
            if (i.isDigit())
                count++
        return count
    }
}