package com.example.colosseum_yun.datas

import org.json.JSONObject

class Reply {
    var id = 0
    var content = ""

    companion object {

        fun getReplyFromJson(jsonObject: JSONObject) : Reply {
            val resultReply = Reply()

            resultReply.id = jsonObject.getInt("id")
            resultReply.content = jsonObject.getString("content")

            return resultReply
        }
    }
}