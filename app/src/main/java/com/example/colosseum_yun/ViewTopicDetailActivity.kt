package com.example.colosseum_yun

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.colosseum_yun.adapter.ReplyAdapter
import com.example.colosseum_yun.datas.Reply
import com.example.colosseum_yun.datas.Side
import com.example.colosseum_yun.datas.Topic
import com.example.colosseum_yun.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_view_topic_detail.*
import org.json.JSONObject

class ViewTopicDetailActivity : BaseActivity() {
    lateinit var mTopic : Topic

    val mReplyList = ArrayList<Reply>()
    lateinit var mReplyAdapter: ReplyAdapter

//    내가 선택한 진영이 어디인지? 투표를 안한상태일수도 있으므로 (= null) ?를 붙임
    var mySelectedSide : Side? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_topic_detail)

        setValues()
        setupEvents()
    }
    override fun setupEvents() {
        
        writeReplyBtn.setOnClickListener { 
            
//            만약 선택된 진영이 없다면 , 투표부터 하도록
            
            if (mySelectedSide == null) {
                Toast.makeText(mContext, "투표를 한 이후에만 의견을 등록할 수 있습니다.", Toast.LENGTH_SHORT).show()
            }
            else {
//                의견작성 화면으로 이동.
                val myIntent = Intent(mContext, EditReplyActivity::class.java)
                myIntent.putExtra("mySide", mySelectedSide)
                startActivity(myIntent)

//                이제 editReply에서 사용 가능해 짐.
            }
        }

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

        mReplyAdapter = ReplyAdapter(mContext, R.layout.reply_list_item, mReplyList)
        replyListView.adapter = mReplyAdapter

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

//                    topicObj 내부의 replies JSONArray 파싱 => 의견 목록에 담아주자.

                    val replyArr = topicObj.getJSONArray("replies")

                    for (i in 0 until replyArr.length()) {

                        val replyObj = replyArr.getJSONObject(i)
                        val reply = Reply.getReplyFromJson(replyObj)
                        mReplyList.add(reply)

                    }

//                    내가 선택한 진영이 있다면 => 파싱해서 mySelectedSide 에 담아주자.
//                    topicObj 내부의 my_side 를 추출 => null  이 아닐때만 추출.
                    
//                    mySelectedSide 를 클리어 해줌
                    mySelectedSide = null
                    if(!topicObj.isNull("my_side")){
                        val mySideObj = topicObj.getJSONObject("my_side")
                        val mySide = Side.getSideFromJson(mySideObj)
                        mySelectedSide = mySide
                    }
//                    ㄴ이거 끝내고 setUpEvent가서  writeReplyBtn.setOnClickListener 설정.

//                    최신 득표현황까지 받아서 mTopic에 저장됨.
//                    UI에 득표 현황 반영.

                    runOnUiThread {

                        firstSideTxt.text = mTopic.sides[0].title
                        firstSideVoteCountTxt.text = "${mTopic.sides[0].voteCount}표"

                        secondSideTxt.text = mTopic.sides[1].title
                        secondSideVoteCountTxt.text = "${mTopic.sides[1].voteCount}표"

//                        댓글 목록 새로 고침(어댑터를 붙여서 고쳐 줘야 새로고침 됨)
                        mReplyAdapter.notifyDataSetChanged()

                    }

                }

            })
        }




}