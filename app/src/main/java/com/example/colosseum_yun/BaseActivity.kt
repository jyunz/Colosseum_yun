package com.example.colosseum_yun

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class BaseActivity :AppCompatActivity() {

    lateinit var backBtn : ImageView

    val mContext = this

    abstract fun setupEvents()
    abstract fun setValues()
//      abstract fun 은 중괄호를 안달고 나중에 구현.
//    일반 fun 은 바로 뭘 할지 구현

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        액션바가 있는 화면만 커스텀 액션바 세팅 실행
//        null체크. 액션바가 있는 화면에서만 커스텀액션바를 실행 해 줘
        supportActionBar?.let {
            setCustomActionBar()
        }

    }

//      액션바 커스텀

    fun setCustomActionBar() {
        val defaultActionBar = supportActionBar!!
        defaultActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        defaultActionBar.setCustomView(R.layout.my_custom_action_bar)

//        양 옆 여백 제거
        val myToolbar = defaultActionBar.customView.parent as Toolbar
        myToolbar.setContentInsetsAbsolute(0,0)

        backBtn = defaultActionBar.customView.findViewById(R.id.backBtn)

        backBtn.setOnClickListener {
            finish()
        }

    }

}