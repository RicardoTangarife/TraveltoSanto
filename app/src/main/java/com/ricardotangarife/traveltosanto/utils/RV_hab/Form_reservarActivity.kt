package com.ricardotangarife.traveltosanto.utils.RV_hab

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ricardotangarife.traveltosanto.LoginActivity
import com.ricardotangarife.traveltosanto.MainActivity
import com.ricardotangarife.traveltosanto.R
import com.ricardotangarife.traveltosanto.SesionRoom
import com.ricardotangarife.traveltosanto.model.Habitacion
import com.ricardotangarife.traveltosanto.utils.botton_navegation.InicioActivity
import com.ricardotangarife.traveltosanto.utils.fragment_bottom_navegation.PerfilFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_form_reservar.*
import kotlinx.android.synthetic.main.activity_form_reservar.img_habitacion
import kotlinx.android.synthetic.main.activity_form_reservar.tv_descripcion
import kotlinx.android.synthetic.main.activity_form_reservar.tv_precio
import kotlinx.android.synthetic.main.activity_form_reservar.tv_tipo
import kotlinx.android.synthetic.main.item_plazareal.*
import java.text.SimpleDateFormat
import java.util.*

class Form_reservarActivity : AppCompatActivity() {

    var Tipo = ""
    var Descripcion = ""
    var precio = 0
    var preciohab = 0
    var foto = ""
    var id_hab = ""
    var visibi = false
    private var cal = Calendar.getInstance()
    private lateinit var fecha1: String
    private lateinit var fecha2: String

    var inicio = ""
    var fin = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_reservar)


        val dataSetListener =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_YEAR, dayOfMonth)

                val format = getString(R.string.formato)
                val sdf = SimpleDateFormat(format, Locale.US)
                fecha1 = sdf.format(cal.time).toString()
                tv_showPicker.text = fecha1
                //Log.d("Fecha",fecha)
            }

        val dataSetListener2 =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_YEAR, dayOfMonth)

                val format = getString(R.string.formato)
                val sdf = SimpleDateFormat(format, Locale.US)
                fecha2 = sdf.format(cal.time).toString()
                tv_showPicker2.text = fecha2
                //Log.d("Fecha",fecha)
            }


        tv_showPicker.setOnClickListener {
            DatePickerDialog(
                this,
                dataSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        tv_showPicker2.setOnClickListener {
            fin = cal.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(
                this,
                dataSetListener2,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


        val datoshab = intent.extras
        if (datoshab != null) {
            Tipo = datoshab?.getString("Tipo").toString()
            Descripcion = datoshab?.getString("Descripcion").toString()
            precio = datoshab?.getInt("Precio")
            foto = datoshab?.getString("foto").toString()
            id_hab = datoshab?.getString("id_hab").toString()
        }
        preciohab = precio
        tv_tipo.text = Tipo
        tv_descripcion.text = Descripcion
        tv_precio.text = precio.toString()
        Picasso.get().load(foto).into(img_habitacion)
        var ingreso = tv_showPicker.text.toString()
        var salida = tv_showPicker2.text.toString()
        tv_precioreserva.text = tv_precio.text
        if ((ingreso != "Fecha de Ingreso") && (salida != "Fecha de Salida")) {
            // preciohab = precio * (fin - inicio)
            // tv_precioreserva.text = preciohab.toString()
        }


        bt_reserva.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("reservas")
            if (!(et_Nombre.text.toString().isEmpty()) && !(et_cedula.text.toString().isEmpty()) && !(et_correo.text.toString().isEmpty()) && !(et_password.text.toString().isEmpty())) {
                tv_precioreserva.text = tv_precio.text
                val nombre = et_Nombre.text.toString()
                val cc = et_cedula.text.toString().toInt()
                val correo = et_correo.text.toString()
                val precio = tv_precio.text.toString().toInt()
                val password = et_password.text.toString()
                val idReservar = myRef.push().key
                val reserva = com.ricardotangarife.traveltosanto.model.Reservar(
                    idReservar!!,
                    nombre,
                    id_hab,
                    cc,
                    ingreso,
                    salida,
                    precio,
                    false
                )
                myRef.child(idReservar).setValue(reserva)

                val Refhab = database.getReference("habitaciones")
                Refhab.addValueEventListener(object : ValueEventListener {

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (snapshot in dataSnapshot.children) {
                            var hab: Habitacion? = snapshot.getValue(Habitacion::class.java)
                            Log.d("hab_id_b", hab?.id.toString())
                            Log.d("hab_id", id_hab)

                            if (hab?.id == id_hab) {
                                hab?.visibilidad = false    //aun no funciona
                            }
                        }
                    }

                    override fun onCancelled(p0: DatabaseError) {
                        Log.w(
                            "Problemas",
                            "problema al encontrar el id de la habitacion",
                            p0.toException()
                        )
                    }
                })

                Toast.makeText(this, "Reserva Completa", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, InicioActivity::class.java)
                intent.putExtra("frg", "profile")
                this.startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Debe digitar todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_overflow, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_abrir) {
            //Toast.makeText(this, "Menu", Toast.LENGTH_SHORT).show()
            val auth = FirebaseAuth.getInstance()
            auth.signOut()
            goToLoginActicity()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun goToLoginActicity(){
        var intentlogin = Intent(this, LoginActivity::class.java)
        intentlogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intentlogin)
    }
}

