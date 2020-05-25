package com.example.kotlin_practice
class A(a:Int=3){
      constructor(a:Int=3,b:String="S") : this(a){
          this.a=a
          this.b=b
      }

    var a: Int?=a;
    var b: String?=null;

    fun calc(){
        println("${b} : ${a}")
    }
}

fun main(){
    println("hello!!")
    calc(3,5,"*")
    val a = A(2,"B")
    a.calc()



}
fun calc(a:Int,b: Int,c:String){
    when(c){
        "+"->println(a+b)
        "-"->println(a-b)
        "*"->println(a*b)
        "/"->println(a/b)
    }


}