package com.ricardotangarife.traveltosanto.utils.RV_LugEmble

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ricardotangarife.traveltosanto.R
import kotlinx.android.synthetic.main.activity_emblema_detalle.*
import kotlinx.android.synthetic.main.fragment_inicio.*

class EmblemaDetalleActivity : AppCompatActivity() {

    var allDetalle: MutableList<EmblematicoVista> = mutableListOf()
    lateinit var detalleRVAdapter: EmblematicoVistaRVA
    private lateinit var linearLayoutManager: LinearLayoutManager

    var title = ""
    var name_data = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emblema_detalle)

        val data = intent.extras
        if(data != null){
            title = data?.getString("img_title").toString()
            name_data = data?.getString("name_data").toString()
        }

        tv_title_detalle.text = title

        detalleRVAdapter = EmblematicoVistaRVA(this,
           allDetalle as ArrayList<EmblematicoVista>
           )
        rv_detalle.layoutManager = LinearLayoutManager(this,
        RecyclerView.VERTICAL,
        false)

        rv_detalle.setHasFixedSize(true)
        rv_detalle.adapter = detalleRVAdapter

    }

    override fun onResume() {
        super.onResume()
        cargarEmblemas()
    }

    private fun cargarEmblemas(){
        var emb = EmblematicoVista()
        var database = FirebaseDatabase.getInstance()
        var myRef = database.getReference(name_data)

        myRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(snapshot in dataSnapshot.children){
                    emb = snapshot.getValue(EmblematicoVista::class.java)!!
                    allDetalle.add(emb)
                }
                detalleRVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.w("Lista", "Failed to read value", p0.toException())
            }
        })
    }
}
