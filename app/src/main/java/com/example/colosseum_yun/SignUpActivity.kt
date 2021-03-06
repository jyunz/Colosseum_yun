package com.example.colosseum_yun

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

                    val code = jsonObj.getInt("code")

                    if (code == 200) {

//                        성공인 경우 가입한 사람의 닉네임을 추출 => 토스트로 **님 환영합니다 + 회원가입화면 종료
//                         => 로그인 화면으로 복귀
//                        닉네임 추출 과정 jsonObj { } => data { } => user { } => 내부에서 nick_name String 추출

                        val dataObj = jsonObj.getJSONObject("data")
                        val userObj = dataObj.getJSONObject("user")

                        val nickname = userObj.getString("nick_name")

                        runOnUiThread { Toast.makeText(mContext,"${nickname}", Toast.LENGTH_SHORT).show()
                        finish()
                        }


                    }
                    else {
//                        실패사유를  => 서버가 주는  message에 담긴 문구로 출력
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