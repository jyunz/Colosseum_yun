package com.example.colosseum_yun.datas

import org.json.JSONObject

class Reply {
    var id = 0
    var content = ""

    lateinit var selectedSide: Side

    var writerNickname = ""

    companion object {

        fun getReplyFromJson(jsonObject: JSONObject) : Reply {
            val resultReply = Reply()

            resultReply.id = jsonObject.getInt("id")
            resultReply.content = jsonObject.getString("content")

//            요부분 다시 잘 들을것.
            resultReply.selectedSide = Side.getSideFromJson(jsonObject.getJSONObject("selected_side"))

            resultReply.writerNickname = jsonObject.getJSONObject("user").getString("nick_name")
            return resultReply
        }
    }
}