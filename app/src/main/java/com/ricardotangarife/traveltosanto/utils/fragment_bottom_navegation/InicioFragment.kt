package com.ricardotangarife.traveltosanto.utils.fragment_bottom_navegation


import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.ricardotangarife.traveltosanto.MapsActivity
import com.ricardotangarife.traveltosanto.R
import com.ricardotangarife.traveltosanto.utils.RV_place.Recommend
import com.ricardotangarife.traveltosanto.utils.RV_place.RecommendRVAdapter
import kotlinx.android.synthetic.main.fragment_inicio.view.*

/**
 * A simple [Fragment] subclass.
 */
class InicioFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(
            R.layout.fragment_inicio,
            container,
            false)

        mapView = view.findViewById(R.id.map)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

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

        view.bt_bares.setOnClickListener{
            var intent = Intent(context, MapsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("Tipo", "bares")
            startActivity(intent)
        }
        view.bt_cajeros.setOnClickListener{
            var intent = Intent(context, MapsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("Tipo", "cajeros")
            startActivity(intent)
        }
        view.bt_hoteles.setOnClickListener{
            var intent = Intent(context, MapsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("Tipo", "hoteles")
            startActivity(intent)
        }
        view.bt_lugares.setOnClickListener{
            var intent = Intent(context, MapsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("Tipo", "lugares")
            startActivity(intent)
        }
        view.bt_restaurante.setOnClickListener{
            var intent = Intent(context, MapsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("Tipo", "restaurantes")
            startActivity(intent)
        }
        view.bt_tiendas.setOnClickListener{
            var intent = Intent(context, MapsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("Tipo", "tiendas")
            startActivity(intent)
        }
        return view
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val principalSanto = LatLng(6.471279, -75.164492)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(principalSanto))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
        mMap.uiSettings.isZoomControlsEnabled = true
        if(ActivityCompat.checkSelfPermission(
                activity!!.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(
                activity!!, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                MapsActivity.LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        mMap.isMyLocationEnabled = true
    }
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }




}
