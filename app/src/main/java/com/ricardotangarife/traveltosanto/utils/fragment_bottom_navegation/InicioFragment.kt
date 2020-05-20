package com.ricardotangarife.traveltosanto.utils.fragment_bottom_navegation


import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.ScrollView
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
import com.ricardotangarife.traveltosanto.utils.RV_LugEmble.Emblematico
import com.ricardotangarife.traveltosanto.utils.RV_LugEmble.EmblematicoRVAdapter
import com.ricardotangarife.traveltosanto.utils.RV_place.Recommend
import com.ricardotangarife.traveltosanto.utils.RV_place.RecommendRVAdapter
import kotlinx.android.synthetic.main.fragment_inicio.*
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

     //   mediacontroller = MediaController(this)

        // Inflate the layout for this fragment
        val view = inflater.inflate(
            R.layout.fragment_inicio,
            container,
            false)

        //view.ScrollView.scrollTo(0, 0)
        //view.ScrollView.smoothScrollTo(0, 0)
        //view.ScrollView.smoothScrollBy(0, 0)

        mapView = view.findViewById(R.id.map)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        var recommendList: MutableList<Recommend> = ArrayList()
        recommendList.add(
            Recommend(
                R.drawable.i_st_tunel,
            "Tunel de la quiebra",
                "i_tunel",
                "f_tunel"

            )
        )
        recommendList.add(
            Recommend(
                R.drawable.i_st_cascada,
                "Termales",
                "i_termales",
                "f_termales"
            )
        )

        recommendList.add(
            Recommend(
                R.drawable.i_st_monta,
                "Obra: Motaña Embarazada",
            "i_montaña",
                "f_montaña"
            )
        )


        recommendList.add(
            Recommend(
                R.drawable.i__st_tomas,
                "Casa Museo Tomás Carrasquilla",
            "i_casa",
                "f_casa"
            )
        )

        view.rv_places.setHasFixedSize(true)
        view.rv_places.layoutManager = LinearLayoutManager(
            activity?.applicationContext,
            RecyclerView.HORIZONTAL,
            false
        )

        val recommendRVAdapter = RecommendRVAdapter(
            activity!!.applicationContext,
            recommendList as ArrayList
        )

        view.rv_places.adapter = recommendRVAdapter


        var emblematicoList: MutableList<Emblematico> = ArrayList()

        emblematicoList.add(
            Emblematico(
                R.drawable.i_sc_parque,
                "Iglesia y Parque Principal",
            "f_iglesia"
            )

        )

        emblematicoList.add(
            Emblematico(
                R.drawable.i_sc_colegio,
                "Colegio",
                "f_colegio"
            )
        )

        emblematicoList.add(
            Emblematico(
                R.drawable.i_sc_charco,
                "Charcos",
                "f_charcos"
            )
        )


        emblematicoList.add(
            Emblematico(
                R.drawable.i_sc_feria,
                "Feria",
            "f_feria"
            )
        )

        emblematicoList.add(
            Emblematico(
                R.drawable.i_sc_entrada,
                "Entrada Principal",
                "f_entrada"
            )
        )

        emblematicoList.add(
            Emblematico(
                R.drawable.i_sc_hospital,
                "Hospital",
                "f_hospital"
            )
        )

        emblematicoList.add(
            Emblematico(
                R.drawable.i_sc_paisaje,
                "Paisaje",
                "f_paisaje"
            )
        )

        emblematicoList.add(
            Emblematico(
                R.drawable.i_sc_piscina,
                "Piscina",
                "f_piscina"
            )
        )

        emblematicoList.add(
            Emblematico(
                R.drawable.i_sc_corregimiento,
                "Corregimientos",
            "f_corregimientos"
            )
        )

        view.rv_emblematicos.setHasFixedSize(true)
        view.rv_emblematicos.layoutManager = LinearLayoutManager(
            activity?.applicationContext,
            RecyclerView.HORIZONTAL,
            false
        )

        val emblematicoRVAdapter = EmblematicoRVAdapter(
            activity!!.applicationContext,
            emblematicoList as ArrayList
        )

        view.rv_emblematicos.adapter = emblematicoRVAdapter



        var detalleList: MutableList<Emblematico> = ArrayList()


        detalleList.add(
            Emblematico(
                R.drawable.i_sc_santa,
                "Semana Santa",
                "f_santa"
            )
        )

        detalleList.add(
            Emblematico(
                R.drawable.i_sc_isidro,
                "San Isidro",
                "f_isidro"
            )
        )

        detalleList.add(
            Emblematico(
                R.drawable.i_sc_velitas,
                "Día de las velitas",
                "f_velitas"
            )
        )

        detalleList.add(
            Emblematico(
                R.drawable.i_sc_virgen,
                "Fiesta de la Virgen",
                "f_virgen"
            )
        )

        detalleList.add(
            Emblematico(
                R.drawable.i_c_antioquia,
                "Día de la Antioqueñidad",
                "f_antioquia"
            )
        )

        detalleList.add(
            Emblematico(
                R.drawable.i_c_campesino,
                "Día del Campesino",
                "f_campesino"
            )
        )

        detalleList.add(
            Emblematico(
                R.drawable.i_c_fiesta1,
                "Fiesta del Chalan",
            "f_fiesta"
            )
        )

        detalleList.add(
            Emblematico(
                R.drawable.i_sc_mascota,
                "Mascotas",
                "f_mascotas"
            )
        )

        detalleList.add(
            Emblematico(
                R.drawable.i_sc_nino,
                "Día del niño",
                "f_niños"
            )
        )

        detalleList.add(
            Emblematico(
                R.drawable.i_sc_salud,
                "Deporte",
                "f_salud"
            )
        )



        view.rv_cultural.setHasFixedSize(true)
        view.rv_cultural.layoutManager = LinearLayoutManager(
            activity?.applicationContext,
            RecyclerView.HORIZONTAL,
            false
        )

        val detalleRVAdapter = EmblematicoRVAdapter(
            activity!!.applicationContext,
            detalleList as ArrayList
        )

        view.rv_cultural.adapter = detalleRVAdapter



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


        //view.ScrollView.scrollTo(0,0)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         val video : Uri = Uri.parse("http://www.ebookfrenzy.com/android_book/movie.mp4")
        videoView.setVideoURI(video)
        videoView.setMediaController(MediaController(activity))
        videoView.start()
        videoView.requestFocus()
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
