package com.example.myhairdiary_c.designers
data class designer(val id:String="",
                    val dimg:Int=0, // 이속성은 사용되지 않을 것 입니다. 다만 수정시에 이 클래스를 사용하는 모든 클래스를 수정해야 하기에 남겨두고 있습니다.
                    val name:String="",
                    val age: Int=0,
                    val phone:String="",
                    val year:Int=0, // 경력입니다.
                    val perm: Int=0,
                    val index:Int=0,
                    val reviewcount:Int=0,
                    val faceurl:String="",
                    val youurl:String="",
                    val instaurl:String="",
                    val naverurl:String="",  // 각종 url은 연동이나 공유에 사용될 예정이었습니다.
                     val monthc:Int=0,
                    var dimgurl:String="",// 이속성은 사용되지 않을 것 입니다. 다만 수정시에 이 클래스를 사용하는 모든 클래스를 수정해야 하기에 남겨두고 있습니다.
                    val openkakaourl:String="",
                    val profile:String="",// 프로필 사진  url을 담습니다.
                    val gender:String="",
                    val major_length:String="",
                    val major:String="",
                    val region:String="전 지역",
                    val region2:String="전 지역" // region=서울 region2=중구 이런식입니다.
                    ,val memo:String="",
                    val customid:String="",// 이건 스크랩 기능에서 사용합니다.
                    val like:Int=0 // 좋아요 수 입니다.

)
// 디자이너 데이터 클래스 입니다. 디자이너와 고객을 나누게 될지 고려하지 않아 많은 속성을 가집니다.
