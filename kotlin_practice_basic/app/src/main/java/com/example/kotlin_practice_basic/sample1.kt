package com.example.kotlin_practice_basic

fun main(){


helloWorild()
println(add(3,5))

}
fun helloWorild():Unit{//Unit 이란 void형!
    println("Hello world")
}
fun add(a:Int , b:Int): Int{
    return a+b
}
/*
* kotlin
함수 작성
리턴 타입 안씀
fun main(){}
func아니고 fun


리턴 없는 함수 - Unit  - void랑 같은 뜻
fun a() : Unit{}

파라미터 - 리턴타입 적용
fun add(a:Int , b:Int): Int{
    return a+b
}


val: 바뀌지 않는 값(final 느낌인가)
var: 변수
val a : Int =10
var b : Int =10
a=100    -> error
b=100 가능
근데 타입 안써줘도 상관은 없음 (자동)
var b=10;
바로 초기화 안하는 변수 만들려면 타입 줘야함
var b
하면 error
var b : String
$ 로 변수를 출력가능
println("my name is &a")
==println("my name is &{a}")
=>println("my name is &{a+b}")
전체 주석 -> ctrl + / 하면 전체 주석/풀기



조건식 if는 같음

when!!! -switch case?
func checknum(score : Int){
   when(score){
   0-> println("0이야")
   1->~~~
   2,3->~~~
  else -> ~~~
                 }

또는 이렇계!
  when(score){
  in 90..100 -> ~~~  // 90에서 100 사이일때

  }


}




배열 :
array는 값을 변경가능
list는 설정에 따라 달라
기본적으로 list는 get함수 존재{remove/removeall/
set가능(!mutablelist!!)}

val array=arrayOf(1,2,3,4,5)==
var array:Array<int>=arrayOf(1,2,3,4,5)

val array2=arrayOf(1,"d",3.4f);
== val array2:Array<Any>=arraOf(~~)

val list=listOf(1,2,3,4,5)
얘는 번경 불가/ get으로 가져오기 가능
mutablelist는 arrayList!!!!자바랑 동일-get/set가능
val arlist=arrayListOf<Int>()
arlist.add(~)



반복 - 포문
for (a in arlist){}
for (i in 1..10 step 2){}   // 1부터 10 까지 10도 포함 // step조절 가능
for (i in 10 down to 1){} 10부터 1까지
for(i in 1 until 100){}    100 포함 안함 ..별필요 없는듯
while() 자바 문법 같음

리스트에서 포문 돌려 뽑아 올때 순서도 주고 싶으면.. 솔직히 불필요 할것 같지만...
for(index,name) in a.withindex()){println("${index+1} : ${name}")}


자바의 nullpointexception
자바는 compile때 잡을수 없는 exception임
코틀린에서는?! 컴파일에서 잡아버리겠다는 개념

변수 선언시 기본적으로 null 불허
var a=null : error:
var a : String? =null 가능
타입선언이 필수면 서 ? 사용
그래서 a에 대해서 메서드 사용시
var b= a.toUpperCase()는 a가 null 이었다면 사실 error
var b=a?.toUpperCase()는 만약 a가 null이면 그냥 null 반환
null말고 default값 정해서 주고 싶을때는 ?:

val lname:String?=null
val fullname= name+(lname?:"default 지정 ")




val a: String?=null
a?.let{//a가 null이 아니면 이거 실행 해라!

}



클래 스 : 생성자를 생략 가능
class A(val b="default"){
  constructor(name : String. age : Int) : this(name){
   //생성자인데 이거 클래스 만들때 생성자 먼저 만들잖아? 그거 상속 무조건!
}

//이거 자체가 클래스 이자 생성자
 fun eat(){println("eat"+b)}

}


class A(){

  constructor(name : String. age : Int) {
   //생성자인데
}
   init{
    // 생성자 역할 하는 것 같은데...
// 생성 될때 마다 실행 되고...근데 생성자 보다 먼저 실행됨!
   }
}




클래스를 상속하려면 상속하려는 클래스 앞에 open 이 되어있어야해
마찬가지로 fun을 override할때도 open 해야해
open class parent(){
   open fun s(){}
   fund ss(){}
}
class child : parent(){
 override fun s(){} // 가능

fun sp(){super.s()}
//호출할때 override 된애가 호출되겠지만 부모꺼 호출하고 싶을때는 super.s()하면 된대

 override fun ss(){}// 불가
}

객체 생성시 new 따위 없어짐
val a= A()


*/