package com.example.colosseum_yun

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.colosseum_yun.datas.Topic
import com.example.colosseum_yun.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_view_topic_detail.*
import org.json.JSONObject

class ViewTopicDetailActivity : BaseActivity() {
    lateinit var mTopic : Topic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_topic_detail)

        setValues()
        setupEvents()
    }
    override fun setupEvents() {

        voteToFirstSideBtn.setOnClickListener {

//            API 확인 -> 토큰 (ContextUtil) + 어떤진영 선택? ( 해당진영의 id값)

//            mTopic.sides[0].id 어느 아이디가 선택했는지 알수있다.=> 서버에 들어가야 한다는 말임.
//            그래서 serverUtil로 가서 작업. postRequestVote

            ServerUtil.postRequestVote(mContext, mTopic.sides[0].id, object : ServerUtil.Companion.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {

//               서버응답 대응 => 서버에서 최신 투표 현황을 받아서 다시 UI에 반영.
//                    만들어둔 함수 재활용

                    getTopicDetailFromServer()


                }
            })

        }

//        두번째 진영 투표 한번 생각해보고 코딩하기
        voteToSecondSideBtn.setOnClickListener {
            ServerUtil.postRequestVote(mContext, mTopic.sides[1].id, object : ServerUtil.Companion.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {

                    getTopicDetailFromServer()
                }
            })
        }

    }

    override fun setValues() {

        mTopic = intent.getSerializableExtra("topic") as Topic

        topicTitleTxt.text = mTopic.title
        Glide.with(mContext).load(mTopic.imageURL).into(topicImgD)

//        현재 투표 현황을 다시 서버에서 받아오자.
        getTopicDetailFromServer()

    }

        fun getTopicDetailFromServer() {

            ServerUtil.getRequestTopicDetail(mContext, mTopic.id, object : ServerUtil.Companion.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {

                    val dataObj = jsonObj.getJSONObject("data")
                    val topicObj = dataObj.getJSONObject("topic")

                    val topic = Topic.getTopicDataFromJson(topicObj)

                    mTopic = topic

//                    최신 득표현황까지 받아서 mTopic에 저장됨.
//                    UI에 득표 현황 반영.

                    runOnUiThread {

                        firstSideTxt.text = mTopic.sides[0].title
                        firstSideVoteCountTxt.text = "${mTopic.sides[0].voteCount}표"

                        secondSideTxt.text = mTopic.sides[1].title
                        secondSideVoteCountTxt.text = "${mTopic.sides[1].voteCount}표"

                    }

                }

            })
        }




}