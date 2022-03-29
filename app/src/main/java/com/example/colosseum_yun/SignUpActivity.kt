package com.example.colosseum_yun


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

        emailCheckBtn.setOnClickListener {

//            입력한 이메일? => 중복 검사
            val inputEmail = email_edt.text.toString()

//            서버에 중복여부 확인, APT호출
//            서버유틸에서 중복체크 작업이 끝난 뒤 가능해짐.
            ServerUtil.getRequestDuplCheck("EMAIL", inputEmail, object : ServerUtil.Companion.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {

                    val code = jsonObj.getInt("code")

                    runOnUiThread {

                        if (code == 200) {
                            Toast.makeText(mContext, "사용해도 좋습니다.", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            val message = jsonObj.getString("message")
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }
                    }

                }

            })
        }
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

                        runOnUiThread { Toast.makeText(mContext,"${nickname}님 환영합니다", Toast.LENGTH_SHORT).show()
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