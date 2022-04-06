package com.example.colosseum_yun.adapter

import android.content.Context
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