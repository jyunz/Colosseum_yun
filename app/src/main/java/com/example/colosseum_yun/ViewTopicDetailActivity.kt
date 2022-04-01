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

    }

    override fun setValues() {
        mTopic = intent.getSerializableExtra("topic") as Topic

        topicTitleTxtd.text = mTopic.title
        Glide.with(mContext).load(mTopic.imageURL).into(topicImgD)

//        현재 투표 현황을 다시 서버에서 받아오자.
        fun getTopicDetailFromServer() {

            ServerUtil.getRequestTopicDetail(mContext, mTopic.id, object : ServerUtil.Companion.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {


                }

            })
        }

    }


}