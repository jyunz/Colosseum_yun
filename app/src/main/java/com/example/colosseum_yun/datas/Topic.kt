package com.example.colosseum_yun.datas

import org.json.JSONObject

class Topic {

    var id = 0     // 나중에 int 가 들어올것이라는 명시
   var title = ""  // 나중에 string 이 들어올 것이라는 명시
    var imageURL = ""

    companion object {

//        적당한 JSONObj(모양) 하나를 넣어주면 => Topic 형태로 변환해주는 함수 작성.

        fun  getTopicDataFromJson (jsonObj : JSONObject) : Topic {
            val resultTopic = Topic()

            resultTopic.id = jsonObj.getInt("id")
            resultTopic.title = jsonObj.getString("title")
            resultTopic.imageURL = jsonObj.getString("img_url")
//            = 은  오른쪽에 내용을 왼쪽에 넣어주라.복사해준다.
//            오른쪽은 서버에서 받아낸것. 왼쪽은 실제로 기록을 해 줄 변수.

            return resultTopic
        }
    }
}