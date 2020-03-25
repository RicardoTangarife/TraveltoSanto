package com.ricardotangarife.traveltosanto.utils.fragment_bottom_navegation


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.ricardotangarife.traveltosanto.R
import com.ricardotangarife.traveltosanto.model.Habitacion
import com.ricardotangarife.traveltosanto.utils.RV_hab.HabPlazaReal
import com.ricardotangarife.traveltosanto.utils.RV_hab.HabPlazaRealRVAdapter
import kotlinx.android.synthetic.main.fragment_reservation.view.*

/**
 * A simple [Fragment] subclass.
 */
class  ReservationFragment : Fragment() {
    var allHabitaciones: MutableList<Habitacion> = mutableListOf()
    lateinit var habPlazaRealRVAdapter: HabPlazaRealRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(
            R.layout.fragment_reservation,
            container,
            false)

        habPlazaRealRVAdapter = HabPlazaRealRVAdapter(
            activity!!.applicationContext,
            allHabitaciones as ArrayList<Habitacion>
        )
        root.rv_habitPlazaReal.layoutManager = LinearLayoutManager(
            activity!!.applicationContext,
            RecyclerView.VERTICAL,
            false
        )

        root.rv_habitPlazaReal.setHasFixedSize(true)
        root.rv_habitPlazaReal.adapter = habPlazaRealRVAdapter

        return root


    }

    override fun onResume() {
        super.onResume()
        cargarHabitaciones()
    }

    private fun cargarHabitaciones() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("habitaciones")
        allHabitaciones.clear()

        myRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(snapshot in dataSnapshot.children){
                    var hab: Habitacion?= snapshot.getValue(Habitacion::class.java)
                    if (hab?.visibilidad == true){
                        allHabitaciones.add(hab!!)
                    }

                }
                habPlazaRealRVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Lista", "Failed to read value", error.toException())
            }
        })

    }

}
