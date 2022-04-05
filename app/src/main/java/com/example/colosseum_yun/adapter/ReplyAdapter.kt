package com.example.colosseum_yun.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.colosseum_yun.R
import com.example.colosseum_yun.datas.Reply
import com.example.colosseum_yun.datas.Topic

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


        return  row
    }

}