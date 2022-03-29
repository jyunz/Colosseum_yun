package com.example.colosseum_yun

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.colosseum_yun.utils.ContextUtil
import com.example.colosseum_yun.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        signUpBtn.setOnClickListener {

            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)

        }

        loginBtn.setOnClickListener {
//            입력한 이메일 / 비밀번호가 뭔지 변수에 저장.
            val inputEmail = emailEdt.text.toString()
            val inputPw = passwordEdt.text.toString()

//            서버에 실제 회원이 맞는지 확인 요청 (Request)
            ServerUtil.postRequestLogin(inputEmail, inputPw, object :ServerUtil.Companion.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {

//                    onResponse 내부는 갔다와서 뭘 할건가를 말함
                    //jsonObj : 서버에서 내려준 본문을 JSON 형태로 가공해준 결과물.
//                    => 이 내부를 파싱(분석)해서 , 상황에 따른 대응.
//                    => ex. 로그인 실패시, 그 이유를 토스트로 띄운다던지.

                    val code = jsonObj.getInt("code")

                    if (code == 200) {
                        //로그인 성공시

//                            서버가 주는 토큰을 추출해서 저장
                        val dataObj = jsonObj.getJSONObject("data")
                        val token = dataObj.getString("token")

                        ContextUtil.setToken(mContext, token)

                        val myIntent = Intent(mContext, MainActivity::class.java)
                        startActivity(myIntent)

                        finish()
                    }
                    else {
                        //로그인 실패시

                        val message = jsonObj.getString("message")

                        runOnUiThread {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            })

        }

    }

    override fun setValues() {

    }


}