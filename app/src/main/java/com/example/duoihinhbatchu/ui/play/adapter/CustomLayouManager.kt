package com.example.duoihinhbatchu.ui.play.adapter

import android.content.Context
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class CustomLayoutManager(context: Context) : FlexboxLayoutManager(context) {

    init {
        justifyContent = JustifyContent.CENTER
    }

    override fun canScrollVertically(): Boolean {
        return false
    }
}