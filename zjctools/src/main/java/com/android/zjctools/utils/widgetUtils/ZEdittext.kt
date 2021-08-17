package com.android.zjctools.utils.widgetUtils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object ZEdittext {

    fun bindClear(input: EditText, clearView: View, method: ((str: String) -> Unit)?) {
        input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                clearView.visibility = if (s.toString().isNotEmpty()) View.VISIBLE else View.GONE
                method?.let {
                    it(s.toString())
                }
            }
        })
        clearView.setOnClickListener {
            input.setText("")
            clearView.visibility = View.GONE
        }
    }


    /**
     * 获取键盘拉起软键盘
     */
    fun  setFocusable (input: EditText){
        input?.isFocusable = true
        input?.isFocusableInTouchMode = true
        input?.requestFocus();
    }

    /**
     * 隐藏软键盘
     */
    fun hideSoftKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    /**
     * 显示软键盘
     */
    fun showSoftKeyboard(evInput: EditText) {
        val inputManager =
            evInput.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(evInput, 0)
    }

}