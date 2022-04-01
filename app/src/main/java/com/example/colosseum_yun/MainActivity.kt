package com.example.colosseum_yun

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.colosseum_yun.adapter.TopicAdapter
import com.example.colosseum_yun.datas.Topic
import com.example.colosseum_yun.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : BaseActivity() {

    val mTopicList = ArrayList<Topic>()

    lateinit var mTopicAdapter : TopicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupEvents()
        setValues()

    }

    override fun setupEvents() {
    }

    override fun setValues() {

        getTopicListFromServer()

        mTopicAdapter = TopicAdapter(mContext, R.layout.topic_list_item, mTopicList)
        topicListview.adapter = mTopicAdapter
    }

    fun getTopicListFromServer() {

//        서버에서 주제 목록을 받아오자. => /v2/mail_info => ServerUtil에 기능 추가 필요.
        ServerUtil.getRequestMainInfo(mContext, object : ServerUtil.Companion.JsonResponseHandler {
            override fun onResponse(jsonObj: JSONObject) {

//                서버에서 주제목록을 받아온 상황
                val dataObj = jsonObj.getJSONObject("data")
                val topicsArr = dataObj.getJSONArray("topics")

//            topicsArr 안에 있는 여러개의 주제들을 반.복.적.으로 파싱. => for문 활용
//                배열에 10개 주제  : Index ->  0~9까지
//                     또는 15개다 :           0~14 까지

                for (index in 0 until topicsArr.length()) {
//                    index 위치에 맞는 주제들을  Topic 클래스 형태로 변환

//                    json 파싱

                    val topicObj = topicsArr.getJSONObject(index)
                    val topicData = Topic.getTopicDataFromJson(topicObj)

//                      mTopicList에 추가

                    mTopicList.add(topicData)

                }

//                어댑터가 먼저 세팅되고 => 나중에 목록에 추가 => 새로고침 필요 (UI 영향)

                runOnUiThread {
                    mTopicAdapter.notifyDataSetChanged()
                }
            }

        })
    }


}
