package com.example.colosseum_yun

import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity :AppCompatActivity() {

    val mContext = this

    abstract fun setupEvents()
    abstract fun setValues()
//      abstract fun 은 중괄호를 안달고 나중에 구현.
//    일반 fun 은 바로 뭘 할지 구현

//      액션바 커스텀

    fun setCustomActionBar() {
        val defaultActionBar = supportActionBar!!
        defaultActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        defaultActionBar.setCustomView(R.layout.my_custom_action_bar)

//        양 옆 여백 제거
        val myToolbar = defaultActionBar.customView.parent as Toolbar
        myToolbar.setContentInsetsAbsolute(0,0)

    }

}