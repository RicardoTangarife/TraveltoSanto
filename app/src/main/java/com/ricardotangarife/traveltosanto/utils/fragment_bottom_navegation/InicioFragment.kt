package com.ricardotangarife.traveltosanto.utils.fragment_bottom_navegation


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ricardotangarife.traveltosanto.R
import com.ricardotangarife.traveltosanto.utils.RV_place.Recommend
import com.ricardotangarife.traveltosanto.utils.RV_place.RecommendRVAdapter
import kotlinx.android.synthetic.main.fragment_inicio.view.*

/**
 * A simple [Fragment] subclass.
 */
class InicioFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(
            R.layout.fragment_inicio,
            container,
            false)

        var recommendList: MutableList<Recommend> = ArrayList()

        recommendList.add(
            Recommend(
                R.drawable.casa_museo,
            "Casa museo Tomás Carrasquilla"
            )
        )

        recommendList.add(
            Recommend(
                R.drawable.cascada,
                "Termales"
            )
        )

        view.rv_places.setHasFixedSize(true)
        view.rv_places.layoutManager = LinearLayoutManager(
            activity?.applicationContext,
            RecyclerView.VERTICAL,
            false
        )

        val recommendRVAdapter = RecommendRVAdapter(
            activity!!.applicationContext,
            recommendList as ArrayList
        )

        view.rv_places.adapter = recommendRVAdapter
        
        return view
    }


}
