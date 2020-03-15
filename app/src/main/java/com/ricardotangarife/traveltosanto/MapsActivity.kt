package com.ricardotangarife.traveltosanto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ricardotangarife.traveltosanto.model.Ubicacion

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var Tipo = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val puntos = intent.extras
        if(puntos != null){
            Tipo = puntos?.getString("Tipo").toString()
        }
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        //setMa(GoogleMap.MAP_TYPE_NORMAL)
        // Add a markar in Sydney and move the camera
        val principalSanto = LatLng(6.471279, -75.164492)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(principalSanto))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
        val database = FirebaseDatabase.getInstance()
        if(Tipo != ""){
            val myRef = database.getReference(Tipo)
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        var sitio: Ubicacion? = snapshot.getValue(Ubicacion::class.java)
                        //Log.w("Ubicacion", sitio!!.lat + sitio!!.lon + sitio!!.nombre)
                        var posicion = LatLng(sitio!!.lat.toDouble(), sitio!!.lon.toDouble())
                        mMap.addMarker(MarkerOptions().position(posicion).title(sitio!!.nombre.toString()))
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("Ubicar", "Failed to read value.", error.toException())
                }
            })
            if(Tipo == "lugares"){
                mMap.animateCamera(CameraUpdateFactory.zoomTo(11.0f));
            }
        }

    }
}
