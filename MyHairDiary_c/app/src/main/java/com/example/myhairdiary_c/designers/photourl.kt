package com.example.myhairdiary_c.designers

data class photourl(val url:String="",val id:String="",val pcount:Int=-1,
                    val name:String="",val style : String="",val length:String="",val gender:String="",
                    val memo:String="",val customid:String="",val like:Int=0,
                    val per_reply:String="",
                    val range:String="",
                    val per_search:String="",
                    val permission:String="",
                    val public:String="", // 외부 허용 여부
                    val perm:Int=0,
)
// 디자이너가 올린 사진들의 정보를 저장합니다.
//println("range : ${a} name : ${title}, memo : ${content}, reply :${reply.text} " +
//        "public : ${public.text} searh : ${search.text}, permisiont : ${permission.text}")