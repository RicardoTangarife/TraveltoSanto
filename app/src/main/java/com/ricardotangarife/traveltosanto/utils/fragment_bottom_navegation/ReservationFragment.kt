package com.ricardotangarife.traveltosanto.utils.fragment_bottom_navegation


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.ricardotangarife.traveltosanto.R
import com.ricardotangarife.traveltosanto.utils.Clases.HabPlazaReal
import com.ricardotangarife.traveltosanto.utils.Clases.HabPlazaRealRVAdapter
import kotlinx.android.synthetic.main.fragment_reservation.view.*

/**
 * A simple [Fragment] subclass.
 */
class ReservationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(
            R.layout.fragment_reservation,
            container,
            false)

        var habPlazaRealList: MutableList<HabPlazaReal> = ArrayList()

        habPlazaRealList.add(
            HabPlazaReal(
                 "Habitación Básica",
            "Cama doble" +
                       "3 días y 2 noches",
                "$200.000",
                        R.drawable.hab3)
        )

        view.rv_habitPlazaReal.setHasFixedSize(true)
        view.rv_habitPlazaReal.layoutManager = LinearLayoutManager(
            activity?.applicationContext,
            RecyclerView.VERTICAL,
            false)

        val habplazarealRVAdapter = HabPlazaRealRVAdapter(
            activity!!.applicationContext,
            habPlazaRealList as ArrayList)

        view.rv_habitPlazaReal.adapter = habplazarealRVAdapter

        return view




    }


}
