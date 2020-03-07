package com.example.mysqltest;

import android.content.Intent;

public class login {


dbconnect db=new dbconnect();
public void log(String id,String pw){

    String pwd=selidpw(id);



}
public String selidpw(String id){

    String []ar=new String[1];
    ar[0]=id;
   String res= db.getData("http://192.168.56.1/selectid.php","selid",ar); //수정 필요x

System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+res);
    return "";
}

}
