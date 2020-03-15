package com.ricardotangarife.traveltosanto.utils.fragment_bottom_navegation


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        /*
        var habPlazaRealList: MutableList<HabPlazaReal> = ArrayList()

        habPlazaRealList.add(
            HabPlazaReal(
                 "Habitación Básica",
            "Cama doble" + "\n" +
                       "3 días y 2 noches",
                "$150.000",
                        R.drawable.hab3)
        )

        habPlazaRealList.add(
            HabPlazaReal(
                "Habitación Doble",
            "2 Camas Medianas" + "\n" +
                    "3 días y 2 noches",
                "$200.000",
                    R.drawable.hab4)
        )

        habPlazaRealList.add(
            HabPlazaReal(
                "Habitación Premium",
            "Cama doble," + "\n"+
                    "3 días y 2 noches",
                "$180.000",
                R.drawable.hab1)
        )

        habPlazaRealList.add(
            HabPlazaReal(
                "Habitación Simple",
                "Cama sensilla"+ "\n"+
                        "2 día y 1 noche",
                "80.000",
                R.drawable.hab2)
        )

        root.rv_habitPlazaReal.setHasFixedSize(true)
        root.rv_habitPlazaReal.layoutManager = LinearLayoutManager(
            activity?.applicationContext,
            RecyclerView.VERTICAL,
            false)

        val habplazarealRVAdapter = HabPlazaRealRVAdapter(
            activity!!.applicationContext,
            habPlazaRealList as ArrayList)

        root.rv_habitPlazaReal.adapter = habplazarealRVAdapter
*/
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
                    allHabitaciones.add(hab!!)
                }
                habPlazaRealRVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Lista", "Failed to read value", error.toException())
            }
        })

    }

}
