package com.example.lovelist.frag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lovelist.R
import com.example.lovelist.list_adapter
import com.example.lovelist.list_data
import com.example.myhairdiary_c.frag.home
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.home.*
import kotlinx.android.synthetic.main.unchec.*

class unchec: Fragment(), NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener{

    var Ltoast: Toast? =null
    override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {
        var view= inflater.inflate(R.layout.unchec,container,false);
        val transaction= activity?.supportFragmentManager?.beginTransaction()
        val rv=view.findViewById<RecyclerView>(R.id.unchecked_rv)

        selectList(this,rv)
        val bot=view.findViewById<BottomNavigationView>(R.id.botnav)
        val nav=view.findViewById<NavigationView>(R.id.naviView)

        bot.getMenu().getItem(1).setChecked(true);
        bot.setOnNavigationItemSelectedListener(this)
        nav.setNavigationItemSelectedListener(this)
        return view



}
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav1->{
                Ltoast?.cancel()
                Ltoast= Toast.makeText(this.context,"녕이야~", Toast.LENGTH_SHORT)
                Ltoast?.show()
            }
            R.id.nav2->{
                Ltoast?.cancel()
                Ltoast= Toast.makeText(this.context,"내가내가~", Toast.LENGTH_SHORT)
                Ltoast?.show()
            }
            R.id.nav3->{
                Ltoast?.cancel()
                Ltoast= Toast.makeText(this.context,"마니나미 사랑해~~~", Toast.LENGTH_SHORT)
                Ltoast?.show()
            }
            R.id.nav4->{
                Ltoast?.cancel()
                Ltoast= Toast.makeText(this.context,"군데군데~", Toast.LENGTH_SHORT)
                Ltoast?.show()
            }
            R.id.nav5->{
                Ltoast?.cancel()
                Ltoast= Toast.makeText(this.context,"녕이눈~~~~", Toast.LENGTH_SHORT)
                Ltoast?.show()
            }
//
//            R.id.bottom1->{
//                val transaction= activity?.supportFragmentManager?.beginTransaction()
//                var f2= home()
//                transaction?.replace(R.id.framelayout,f2)
//                transaction?.commit()
//            }
//            R.id.bottom2->{
//                val transaction= activity?.supportFragmentManager?.beginTransaction()
//                var f2= chec()
//                transaction?.replace(R.id.framelayout,f2)
//                transaction?.commit()
//
//
//            }
//            R.id.bottom3->{
//                val transaction= activity?.supportFragmentManager?.beginTransaction()
//                var f2= unchec()
//                transaction?.replace(R.id.framelayout,f2)
//                transaction?.commit()
//
//            }
            R.id.hama->{
                val pref=requireContext().getSharedPreferences("Rnd",0)
                val edit=pref.edit()
                edit.putString("id","누꿍")
                edit.apply()
                Toast.makeText(this.context,"누꿍 안냥?", Toast.LENGTH_SHORT).show()

                layout_drawer.closeDrawers()
            }
            R.id.staris->{
                val pref=requireContext().getSharedPreferences("Rnd",0)
                val edit=pref.edit()
                edit.putString("id","내꿍")
                edit.apply()
                Toast.makeText(this.context,"냐오옹 이거아냐아아아 저리가아ㅏㅇ아", Toast.LENGTH_SHORT).show()

                layout_drawer.closeDrawers()
            }else->{
            Ltoast?.cancel()

            Ltoast= Toast.makeText(this.context,"바보멍청이말미쟐해삼!!!!꺆!", Toast.LENGTH_SHORT)
            Ltoast?.show()

            layout_drawer.closeDrawers()
        }
        }
        return true
    }
    fun selectList(container: unchec,rv:RecyclerView){
        val firestore= FirebaseFirestore.getInstance()
        firestore?.collection("ourlist").orderBy("index").get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<list_data>()
                    for(dc in it.result!!.documents){
                        dc.toObject(list_data::class.java)?.let { it1 ->
                            if(!it1.ischecked){
                                userDTO.add(it1)
                            }
                        }
                    }

                    rv.layoutManager=
                        LinearLayoutManager(container.context, LinearLayoutManager.VERTICAL,false)
                    rv.setHasFixedSize(true)
                    rv.adapter=
                        container.context?.let { it1 ->
                            list_adapter(
                                it1,
                                userDTO
                            )
                        }
                }else{
                    println("fail")
                }
            }
    }
}