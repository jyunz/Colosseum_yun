package com.example.colosseum_yun

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.colosseum_yun.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject

class SignUpActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        setupEvents()
        setValues()
    }
    override fun setupEvents() {
        signUp_btn.setOnClickListener {

            val inputEmail = email_edt.text.toString()
            val inputPw = password_edt.text.toString()
            val inputNickname = nickname_edt.text.toString()

            ServerUtil.putRequestSignUp(inputEmail, inputPw, inputNickname, object : ServerUtil.Companion.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {

                }

            })
        }


    }

    override fun setValues() {


    }

}