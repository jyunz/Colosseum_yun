package com.example.colosseum_yun.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.colosseum_yun.R
import com.example.colosseum_yun.datas.Reply
import com.example.colosseum_yun.datas.Topic
import com.example.colosseum_yun.utils.ServerUtil
import org.json.JSONObject

class ReplyAdapter(
        val mContext : Context,
        resId : Int,
        val mList : List<Reply>) : ArrayAdapter<Reply>(mContext, resId, mList) {

    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView
        if (tempRow == null) {
            tempRow = mInflater.inflate(R.layout.reply_list_item, null)
        }
        val row = tempRow!!

        val data = mList[position]

        val selectedSideTxt = row.findViewById<TextView>(R.id.selectedSideTxt)
        val userNicknameTxt = row.findViewById<TextView>(R.id.userNicknameTxt)
        val contentTxt = row.findViewById<TextView>(R.id.contentTxt)

        val likeCountBtn = row.findViewById<TextView>(R.id.likeCountBtn)
        val disLikeCountBtn = row.findViewById<TextView>(R.id.dislikeCountBtn)


        contentTxt.text = data.content

        selectedSideTxt.text = "(${data.selectedSide.title})"
        userNicknameTxt.text = data.writerNickname

        likeCountBtn.text = "좋아요 ${data.likeCount}개"
        disLikeCountBtn.text = "싫어요 ${data.dislikeCount}개"

        if (data.myLike) {

//            like 면 글씨를 빨간색 + 배경도 빨간 테두리가 나와야 한다는 뜻임. ->싫어요 -> 회색

            likeCountBtn.setBackgroundResource(R.drawable.red_border_box)
            likeCountBtn.setTextColor(Color.parseColor("#FF0000"))

            disLikeCountBtn.setBackgroundResource(R.drawable.gray_border_box)
            disLikeCountBtn.setTextColor(Color.parseColor("#A0A0A0"))

        }
        else if (data.myDislike) {

//            글씨가 파랑 배경도 파랑 , 좋아요 -> 회색

            likeCountBtn.setBackgroundResource(R.drawable.gray_border_box)
            likeCountBtn.setTextColor(Color.parseColor("#A0A0A0"))

            disLikeCountBtn.setBackgroundResource(R.drawable.blue_border_box)
            disLikeCountBtn.setTextColor(Color.parseColor("#0000FF"))

        }
        else {
//                좋아요 / 싫어요를 찍지 않은 상태.-> 둘다 회색

            likeCountBtn.setBackgroundResource(R.drawable.gray_border_box)
            likeCountBtn.setTextColor(Color.parseColor("#A0A0A0"))

            disLikeCountBtn.setBackgroundResource(R.drawable.gray_border_box)
            disLikeCountBtn.setTextColor(Color.parseColor("#A0A0A0"))
        }
        
//        좋아요 API호출
        
        likeCountBtn.setOnClickListener {
//            Toast.makeText(mContext, "좋아요 연습", Toast.LENGTH_SHORT).show()
//             ㄴ 연습해서 화면에 뜨면 서버유틸에서 작업. 작업후 ㄱ
            ServerUtil.postRequestLikeDislike(mContext, data.id, true, object : ServerUtil.Companion.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {

                }
            })

        }

//         싫어요 API호출
        
        disLikeCountBtn.setOnClickListener {
            ServerUtil.postRequestLikeDislike(mContext, data.id, false, object : ServerUtil.Companion.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {

                }
            })
        }




        return  row
    }

}