package com.example.kotlin_prac

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.activity_graph_prac.*
import org.eazegraph.lib.models.BarModel
import top.defaults.colorpicker.ColorPickerPopup
import top.defaults.colorpicker.ColorPickerPopup.ColorPickerObserver
import java.util.*
import kotlin.collections.ArrayList


class graph_prac : AppCompatActivity() {

    var SAVED_STATE_KEY_COLOR = "saved_state_key_color"
    var  INITIAL_COLOR: Long = 0xFFFF8000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph_prac)
    pichart()
        barchart()
        colorpic(savedInstanceState)




       // Toast.makeText(this,"setinitial color",Toast.LENGTH_SHORT).show()
    }

fun colorpic(savedInstanceState: Bundle?) {
    colorPicker.subscribe({ color, fromUser, shouldPropagate ->
        var c=color.toString()

       // pickedColor.setBackgroundColor(Integer.parseInt(c))

      //  Toast.makeText(this,"setbackground : ${color.toString()}",Toast.LENGTH_SHORT).show()

        // colorHex.text=colorHex(color).toString()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
         //   window.statusBarColor = Integer.parseInt(c)
        }
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {// 여기서 색상 바뀌는 거 확인 가능


        //    Toast.makeText(this,"",Toast.LENGTH_SHORT).show()


            actionBar.setBackgroundDrawable(ColorDrawable(Integer.parseInt(c)))
            println("색상 : ${c}")
            val a = Color.alpha(color)
            val r = Color.red(color)
            val g = Color.green(color)
            val b = Color.blue(color)


            var R= select_color(r)
            var G= select_color(g)
            var B= select_color(b)//27가지 경우


            println("alpha : $a")
            println("red : $r")
            println("green : $g")
            println("blue : $b")

            iffunc(R,G,B)

        }
    })

    var color: Long = INITIAL_COLOR
    if (savedInstanceState != null) {
        color = savedInstanceState.getInt(SAVED_STATE_KEY_COLOR, INITIAL_COLOR.toInt()).toLong()

       // Toast.makeText(this,"saved instance state",Toast.LENGTH_SHORT).show()
    }

    colorPicker.setInitialColor(color.toInt())
}
fun iffunc(R:Int,G:Int,B:Int){
    /*13 개
black
blue
sea
sky
green
mint
pink
purple
yellow
white
grass
red
orange

     */
    if(R==0&&G==0&&B==0){
        println("black")
        Toast.makeText(this,"black",Toast.LENGTH_SHORT).show()
    }else if(R==0&&G==0&&B==2){
        println("blue")
        Toast.makeText(this,"blue",Toast.LENGTH_SHORT).show()
    }

else if(R==0&&G==1&&B==1){
        println("sky")
        Toast.makeText(this,"sky",Toast.LENGTH_SHORT).show()
    }else if(R==0&&G==1&&B==2){
        println("sea")
        Toast.makeText(this,"sea",Toast.LENGTH_SHORT).show()
    }

    else if(R==0&&G==2&&B==0){
        println("green")
        Toast.makeText(this,"green",Toast.LENGTH_SHORT).show()
    }
    else if(R==0&&G==2&&B==1){
        println("mint")
        Toast.makeText(this,"mint",Toast.LENGTH_SHORT).show()
    }else if(R==0&&G==2&&B==2){
        println("sky")
        Toast.makeText(this,"sky",Toast.LENGTH_SHORT).show()
    }
 else if(R==1&&G==0&&B==1){        println("pn-ink")
        Toast.makeText(this,"pink",Toast.LENGTH_SHORT).show()
    }else if(R==1&&G==0&&B==2){
        println("purple")
        Toast.makeText(this,"purple",Toast.LENGTH_SHORT).show()
    }

    else if(R==1&&G==1&&B==0){
        println("yellow")
        Toast.makeText(this,"yellow",Toast.LENGTH_SHORT).show()
    }else if(R==1&&G==1&&B==1){
        println("white")
        Toast.makeText(this,"white",Toast.LENGTH_SHORT).show()

    }else if(R==1&&G==1&&B==2){
        println("sea")
        Toast.makeText(this,"sea",Toast.LENGTH_SHORT).show()
    }

    else if(R==1&&G==2&&B==0){

        println("grass")
        Toast.makeText(this,"grass",Toast.LENGTH_SHORT).show()
    }
    else if(R==1&&G==2&&B==1){

        println("green")
        Toast.makeText(this,"green",Toast.LENGTH_SHORT).show()
    }else if(R==1&&G==2&&B==2){
        println("sky")
        Toast.makeText(this,"sky",Toast.LENGTH_SHORT).show()
    }


    else if(R==2&&G==0&&B==0){
        println("red")
        Toast.makeText(this,"red",Toast.LENGTH_SHORT).show()
    }else if(R==2&&G==0&&B==1){

        println("red")
        Toast.makeText(this,"red",Toast.LENGTH_SHORT).show()
    }else if(R==2&&G==0&&B==2){
        println("pink")
        Toast.makeText(this,"pink",Toast.LENGTH_SHORT).show()
    }

    else if(R==2&&G==1&&B==0){
        println("oragne")
        Toast.makeText(this,"orange",Toast.LENGTH_SHORT).show()
    }else if(R==2&&G==1&&B==1){

        println("red")
        Toast.makeText(this,"red",Toast.LENGTH_SHORT).show()
    }else if(R==2&&G==1&&B==2){
        println("pink")
        Toast.makeText(this,"pink",Toast.LENGTH_SHORT).show()
    }

    else if(R==2&&G==2&&B==0){
        println("yellow")
        Toast.makeText(this,"yellow",Toast.LENGTH_SHORT).show()
    }
    else if(R==2&&G==2&&B==1){
        println("yellow")
        Toast.makeText(this,"yellow",Toast.LENGTH_SHORT).show()
    }else if(R==2&&G==2&&B==2) {
        println("white")
        Toast.makeText(this,"white",Toast.LENGTH_SHORT).show()
    }else{
        Toast.makeText(this,"retry",Toast.LENGTH_LONG).show()
    }

}

    fun select_color(r:Int):Int{
        if(r<100) {
            return 0
        }else if(r>=100&&r<220){
            return 1
        }
        else{
            return 2
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SAVED_STATE_KEY_COLOR, colorPicker.getColor())
       // Toast.makeText(this,"onsaveinstancestate",Toast.LENGTH_SHORT).show()
    }

    private fun colorHex(color: Int): String? {
        val a = Color.alpha(color)
        val r = Color.red(color)
        val g = Color.green(color)
        val b = Color.blue(color)
        return java.lang.String.format(Locale.getDefault(), "0x%02X%02X%02X%02X", a, r, g, b)
    }
    fun popup(v: View?) {
        ColorPickerPopup.Builder(this)
            .initialColor(colorPicker.getColor())
            .enableAlpha(true)
            .okTitle("Choose")
            .cancelTitle("Cancel")
            .showIndicator(true)
            .showValue(true)
            .onlyUpdateOnTouchEventUp(true)
            .build()
            .show(object : ColorPickerObserver() {
                override fun onColorPicked(color: Int) {
                    colorPicker.setInitialColor(color)
                }
            })
    }










    fun pichart(){
        val colorArray=ArrayList<Int>()
        colorArray.add(Color.LTGRAY)
        colorArray.add(Color.BLUE)
        colorArray.add(Color.RED)
        val pieDataSet: PieDataSet = PieDataSet(data1(), "설문!!")
        pieDataSet.setColors(colorArray)
        val pieData: PieData = PieData(pieDataSet)
        picChart.setDrawEntryLabels(true);
        picChart.setUsePercentValues(true)
        pieData.setValueTextSize(30f)
        picChart.setCenterTextSize(25f)
        picChart.holeRadius=30f
        picChart.setData(pieData)
        picChart.invalidate();
    }
    fun data1(): ArrayList<PieEntry>? {
        val datavalue: ArrayList<PieEntry> = ArrayList()
        datavalue.add(PieEntry(20f, "무응답"))
        datavalue.add(PieEntry(45f, "냐옹"))
        datavalue.add(PieEntry(35f, "멍멍"))
        return datavalue
    }









//    fun scrolling_pichart(){
//        tab1_chart_1!!.clearChart()
//        tab1_chart_1!!.addPieSlice(PieModel("TYPE 1", 60F, Color.parseColor("#CDA67F")))
//        tab1_chart_1!!.addPieSlice(PieModel("TYPE 2", 40F, Color.parseColor("#00BFFF")))
//        tab1_chart_1!!.startAnimation()
//    }
    fun barchart(){
        tab1_chart_2!!.clearChart()
        tab1_chart_2!!.addBar(BarModel("1", 10f, -0xa9480f))
        tab1_chart_2!!.addBar(BarModel("2", 10f, -0xa9480f))
        tab1_chart_2!!.addBar(BarModel("3", 10f, -0xa9480f))
        tab1_chart_2!!.addBar(BarModel("4", 20f, -0xa9480f))
        tab1_chart_2!!.addBar(BarModel("5", 10f, -0xa9480f))
        tab1_chart_2!!.addBar(BarModel("6", 10f, -0xa9480f))
        tab1_chart_2!!.startAnimation()
    }


}
