package com.example.colosseum_yun.utils

import android.content.Context
import android.util.Log
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
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


//       이메일 / 닉네임 중복 확인 기능

        fun getRequestDuplCheck(type : String, value : String, handler: JsonResponseHandler?) {

//            어디로 가는가? + 어떤 데이터를(타입이나 밸류) 보내는가? 를 같이 보내준다.
//            URL을 적으면서 + 파라미터 첨부도 같이 보내기 힘들기 때문에 => 보조도구인 builder사용.

//            val urlBuilder = HttpUrl.parse("${BASE_URI}/user_check")까지 입력하면
//            parse에 에러가 뜨는데 alt+enter 누르고 맨 위의 것으로 고르면 모양이 바뀜.

            val urlBuilder = "${BASE_URI}/user_check".toHttpUrlOrNull()!!.newBuilder()
//            어디로 가는지는 완성. 어느서버/ 어느 기능으로 가는지가 정해짐.
//            urlBuilder는 url가공보조도구임
            urlBuilder.addEncodedQueryParameter("type", type)
            urlBuilder.addEncodedQueryParameter("value", value)

            val urlString = urlBuilder.build().toString()

            Log.d("완성된URL", urlString)

            val request = Request.Builder()
                .url(urlString)
                .get()
                .build()
            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
//                   성공했을 때 본문 내용 분석해서 화면에서 사용 할 것.
                    var bodyString = response.body!!.string()

//                    로그 찍으면 잘 안나와서 잘 보이게 큰 덩어리를 불러옴.
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답본문", jsonObj.toString())

//                  재료로 받아오는 핸들러한테 넘겨줌

                    handler?.onResponse(jsonObj)

                }



            })

        }
//          post나 put은 formdata에다 데이타를 담아준다
//        get은 url을 만들때 쿼리파라미터를 이용

//        진행중인 주제 목록 확인기능

        fun getRequestMainInfo(context: Context, handler: JsonResponseHandler?) {

            val urlBuilder = "${BASE_URI}/v2/main_info".toHttpUrlOrNull()!!.newBuilder()

//            urlBuilder.addEncodedQueryParameter("type", type)
//            urlBuilder.addEncodedQueryParameter("value", value)

            val urlString = urlBuilder.build().toString()

            Log.d("완성된URL", urlString)

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getToken(context))
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
//                   성공했을 때 본문 내용 분석해서 화면에서 사용 할 것.
                    var bodyString = response.body!!.string()

//                    로그 찍으면 잘 안나와서 잘 보이게 큰 덩어리를 불러옴.
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답본문", jsonObj.toString())

//                  재료로 받아오는 핸들러한테 넘겨줌

                    handler?.onResponse(jsonObj)

                }



            })

        }

//        원하는 주제 상세현황 확인 기능

        fun getRequestTopicDetail(context: Context,topicId : Int, handler: JsonResponseHandler?) {

//            val urlBuilder = "${BASE_URI}/topic/${topicId}".toHttpUrlOrNull()!!.newBuilder()를 쓰거나
            val urlBuilder = "${BASE_URI}/topic".toHttpUrlOrNull()!!.newBuilder()
            urlBuilder.addEncodedPathSegment(topicId.toString())
            urlBuilder.addEncodedQueryParameter("oder_type","NEW")

            val urlString = urlBuilder.build().toString()

            Log.d("완성된URL", urlString)

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getToken(context))
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
//                   성공했을 때 본문 내용 분석해서 화면에서 사용 할 것.
                    var bodyString = response.body!!.string()

//                    로그 찍으면 잘 안나와서 잘 보이게 큰 덩어리를 불러옴.
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답본문", jsonObj.toString())

//                  재료로 받아오는 핸들러한테 넘겨줌

                    handler?.onResponse(jsonObj)

                }



            })

        }

//        특정진영에 투표하기

        fun postRequestVote(context: Context, sideId : Int, handler: JsonResponseHandler?) {


            val urlString = "${BASE_URI}/topic_vote"

//            POST방식 => 파라미터를 폼데이터 (폼바디) 에 담아주자.
//            add가 많아지면 앞의 점에서 엔터 해서 줄 바꿔서 가독성 좋게 만들어줌
//            .build는 넣을거 다 넣고 난 뒤 가방 닫는 의미
            val formData = FormBody.Builder()
                .add("side_id", sideId.toString())
                .build()

//            어디로 => 어떻게 => 어떤 데이터를 들고 가는지를 모두 종합해둔 ,  Request 변수 생성.
            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getToken(context))
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


//        의견 등록하기
        fun postRequestReply(context: Context, topicId: Int,content: String,  handler: JsonResponseHandler?) {


            val urlString = "${BASE_URI}/topic_reply"

//            POST방식 => 파라미터를 폼데이터 (폼바디) 에 담아주자.
//            add가 많아지면 앞의 점에서 엔터 해서 줄 바꿔서 가독성 좋게 만들어줌
//            .build는 넣을거 다 넣고 난 뒤 가방 닫는 의미
            val formData = FormBody.Builder()
                .add("topic_id", topicId.toString())
                .add("content", content)
                .build()

//            어디로 => 어떻게 => 어떤 데이터를 들고 가는지를 모두 종합해둔 ,  Request 변수 생성.
            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getToken(context))
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