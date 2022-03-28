package com.example.colosseum_yun.utils

import android.util.Log
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ServerUtil {
//    어떤 내용을 작성해야 서버 연결 전달 되는지 알아보자


    companion object{

        interface JsonResponseHandler {
            fun onResponse(jsonObj : JSONObject)
        }

//        모든기능의 기본이 되는 주소

        val BASE_URI = " http://54.180.52.26"

//        로그인하는 기능 (handler는 갔다와서 할 행동)

        fun postRequestLogin(email : String, pw : String, handler: JsonResponseHandler?) {

//            서버에 입력받은 email pw전달 => 로그인 기능 POST / user 로 전달 => 요청 (Request) 실행
//            라이브러리 (OkHttp)활용해서 짜보자

//            http://54.180.52.26/user + POST + 파라미터를 첨부.

//            호스트 주소 + 기능주소 결합
            val urlString = "${BASE_URI}/user"

//            POST방식 => 파라미터를 폼데이터 (폼바디) 에 담아주자.
//            add가 많아지면 앞의 점에서 엔터 해서 줄 바꿔서 가독성 좋게 만들어줌
//            .build는 넣을거 다 넣고 난 뒤 가방 닫는 의미
            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .build()

//            어디로 => 어떻게 => 어떤 데이터를 들고 가는지를 모두 종합해둔 ,  Request 변수 생성.
            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .build()

//            클라이언트로써의 동작. : Request 요청 실행  => OkHttp 라이브러리 지원.

            val client = OkHttpClient()

//            실제로 서버에 요청 날리기. = > 갔다 와서는 뭘 할건지?/할 일은 {} 안에 있음

                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
//                        서버 연결 자체를 실패 한 경우(서버 마비, 인터넷 단선)
                    }

                    override fun onResponse(call: Call, response: Response) {

//                        로그인 성공, 로그인 실패 (연결 성공 - > 검사실패)
//                        응답이 돌아온 경우


//                        응답 본문을 String으로 저장
                        val bodyString = response.body!!.string()

//                        그런데 나오는 로그캣이 bodyString 변수에는 한글이 깨져 나오기 때문에
//                        JSONObject로 변환하면, 한글이 정상적으로 처리됨

                        val jsonObj = JSONObject(bodyString)

                       Log.d("응답본문", jsonObj.toString())

//                        handler 변수가 null이 아니라면 ,(실체가 있다면)
//                        그 내부에 적힌 내용 실행.
                        handler?.onResponse(jsonObj)
                    }

                })
        }


//        회원가입하는 기능

        fun putRequestSignUp(email : String, pw : String, nick : String, handler: JsonResponseHandler?) {

//            서버에 입력받은 email, pw, nickname 전달 => 회원가입 기능 PUT / user 로 전달 => 요청 (Request) 실행
//            라이브러리 (OkHttp)활용해서 짜보자

//            http://54.180.52.26/user + PUT + 파라미터를 첨부.

//            호스트 주소 + 기능주소 결합
            val urlString = "${BASE_URI}/user"

//            PUT방식 => 파라미터를 폼데이터 (폼바디) 에 담아주자.
//            add가 많아지면 앞의 점에서 엔터 해서 줄 바꿔서 가독성 좋게 만들어줌
//            .build는 넣을거 다 넣고 난 뒤 가방 닫는 의미
            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .add("nick_name", nick)
                .build()

//            어디로 => 어떻게 => 어떤 데이터를 들고 가는지를 모두 종합해둔 ,  Request 변수 생성.
            val request = Request.Builder()
                .url(urlString)
                .put(formData)
                .build()

//            클라이언트로써의 동작. : Request 요청 실행  => OkHttp 라이브러리 지원.

            val client = OkHttpClient()

//            실제로 서버에 요청 날리기. = > 갔다 와서는 뭘 할건지?/할 일은 {} 안에 있음

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                        서버 연결 자체를 실패 한 경우(서버 마비, 인터넷 단선)
                }

                override fun onResponse(call: Call, response: Response) {

//                        로그인 성공, 로그인 실패 (연결 성공 - > 검사실패)
//                        응답이 돌아온 경우


//                        응답 본문을 String으로 저장
                    val bodyString = response.body!!.string()

//                        그런데 나오는 로그캣이 bodyString 변수에는 한글이 깨져 나오기 때문에
//                        JSONObject로 변환하면, 한글이 정상적으로 처리됨

                    val jsonObj = JSONObject(bodyString)

                    Log.d("응답본문", jsonObj.toString())

//                        handler 변수가 null이 아니라면 ,(실체가 있다면)
//                        그 내부에 적힌 내용 실행.
                    handler?.onResponse(jsonObj)
                }

            })
        }


    }
}