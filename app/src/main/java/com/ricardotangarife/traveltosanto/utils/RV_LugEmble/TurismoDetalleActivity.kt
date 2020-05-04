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
import kotlinx.android.synthetic.main.activity_turismo_detalle.*

class TurismoDetalleActivity : AppCompatActivity() {

    var title = ""
    var name_data = ""
    var name_data2 = ""
    var img = 0
    var allDetalle: MutableList<Turismo> = mutableListOf()
    lateinit var detalleRVAdapter: TurismoRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_turismo_detalle)

        val data = intent.extras
        if(data != null){
            title = data?.getString("img_title").toString()
            name_data = data?.getString("name_data").toString()
            name_data2 = data?.getString("name_data2").toString()

            img = data?.getInt("img")
        }

        img_turismo.setImageResource(img)
        tv_title.text = title

        detalleRVAdapter = TurismoRVAdapter(this,
        allDetalle as ArrayList<Turismo>)

        rv_imagenes.layoutManager = LinearLayoutManager(this,
        RecyclerView.VERTICAL,
        false)

        rv_imagenes.setHasFixedSize(true)
        rv_imagenes.adapter = detalleRVAdapter


    }

    override fun onResume() {
        super.onResume()
        cargarDescripcion()
        cargarImagenes()
    }

    private fun cargarImagenes(){
        var det = Turismo()
        var database = FirebaseDatabase.getInstance()
        var myRef = database.getReference(name_data2)
        allDetalle.clear()

        myRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.w("Lista", "Failed to read value", p0.toException())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(snapshot in dataSnapshot.children){
                    det = snapshot.getValue(Turismo::class.java)!!
                    allDetalle.add(det)
                }
                detalleRVAdapter.notifyDataSetChanged()
            }
        })

    }


    private fun cargarDescripcion(){
        var det = DetalleTurismo()
        var database = FirebaseDatabase.getInstance()
        var myRef = database.getReference(name_data)

        myRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.w("Lista", "Failed to read value", p0.toException())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children){
                    det = snapshot.getValue(DetalleTurismo::class.java)!!
                    Log.d("detalle", det!!.descripcion.toString())
                    tv_descripcion_detalle.text = det!!.descripcion
                }

            }

        })
    }
}
