package com.ricardotangarife.traveltosanto.utils.RV_hab

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.ricardotangarife.traveltosanto.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_form_reservar.*
import java.text.SimpleDateFormat
import java.util.*

class Form_reservarActivity : AppCompatActivity() {

    var Tipo = ""
    var Descripcion = ""
    var precio = 0
    var preciohab = 0
    var foto = ""
    var id_hab = ""
    private var cal = Calendar.getInstance()
    private lateinit var fecha1 : String
    private lateinit var fecha2 : String

    var inicio = ""
    var fin = 0

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
        if (datoshab != null){
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
        val salida = tv_showPicker2.text.toString()


        if (ingreso != "Fecha de Ingreso" && salida != "Fecha de Salida"){
          //  preciohab = precio * (fin - inicio)
        }
        tv_precioreserva.text = ingreso

        bt_reserva.setOnClickListener{

            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("reservas")

            val nombre = et_Nombre.text.toString()
            val cc = et_cedula.text.toString().toInt()
            val correo = et_correo.text.toString()

            val precio = tv_precio.text.toString().toInt()
            val password = et_password.text.toString()


            if (nombre.isEmpty() || correo.isEmpty()){
                Toast.makeText( this, "Debe digitar todos los campos", Toast.LENGTH_SHORT).show()
            }
            else {


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

                Toast.makeText(this, "Reserva Completa", Toast.LENGTH_SHORT).show()
            }
            }
    }
}

