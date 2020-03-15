package com.ricardotangarife.traveltosanto.utils.RV_hab

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import com.ricardotangarife.traveltosanto.R
import kotlinx.android.synthetic.main.activity_form_reservar.*
import kotlinx.android.synthetic.main.fragment_reservation.*
import java.text.SimpleDateFormat
import java.util.*

class Form_reservarActivity : AppCompatActivity() {

    var Tipo = ""
    var Descripcion = ""
    var precio = 0
    var foto = 0
    var id_hab = ""
    private var cal = Calendar.getInstance()
    private lateinit var fecha1 : String
    private lateinit var fecha2 : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_reservar)


        val dataSetListener =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                cal.set ( Calendar.YEAR, year)
                cal.set (Calendar.MONTH, month)
                cal.set ( Calendar.DAY_OF_YEAR, dayOfMonth)

                val format = getString(R.string.formato)
                val sdf = SimpleDateFormat(format, Locale.US)
                fecha1 = sdf.format(cal.time).toString()
                tv_showPicker.text = fecha1
                //Log.d("Fecha",fecha)
            }

        val dataSetListener2 =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                cal.set ( Calendar.YEAR, year)
                cal.set (Calendar.MONTH, month)
                cal.set ( Calendar.DAY_OF_YEAR, dayOfMonth)

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
            DatePickerDialog(
                this,
                dataSetListener2,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

     val datoshab = intent.extras
        if (datoshab != null){
            Tipo = datoshab?.getString("Tipo").toString()
            Descripcion = datoshab?.getString("Descripcion").toString()
            precio = datoshab?.getInt("Precio")
            foto = datoshab?.getInt("foto")
            id_hab = datoshab?.getString("id_hab").toString()
        }

        tv_tipo.text = Tipo
        tv_descripcion.text = Descripcion
        tv_precio.text = precio.toString()
        img_habitacion.setImageResource(foto)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("reservas")

        bt_reservar.setOnClickListener{
            val nombre = et_Nombre.text.toString()
            val CC = et_cedula
            val correo = et_correo
            val ingreso = tv_showPicker
            val salida = tv_showPicker2
            val precio = tv_precio
            val password = et_password

            val idReservar = myRef.push().key

            val reserva = Reservar (
                idReservar!!,
                nombre,
                correo,
                CC,
                ingreso,
                salida,
                precio)

            myRef.child(idReservar).setValue(reserva)

            
        }
    }
}

