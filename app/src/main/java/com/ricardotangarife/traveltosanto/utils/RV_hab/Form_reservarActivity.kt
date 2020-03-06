package com.ricardotangarife.traveltosanto.utils.RV_hab

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ricardotangarife.traveltosanto.R
import kotlinx.android.synthetic.main.activity_form_reservar.*
import java.text.SimpleDateFormat
import java.util.*

class Form_reservarActivity : AppCompatActivity() {

    var Tipo = ""
    var Descripcion = ""
    var precio = ""
    var foto = 0
    private var cal = Calendar.getInstance()
    private lateinit var fecha : String

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
                fecha = sdf.format(cal.time).toString()
                tv_showPicker.text = fecha
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

     val datoshab = intent.extras
        if (datoshab != null){
            Tipo = datoshab?.getString("Tipo").toString()
            Descripcion = datoshab?.getString("Descripcion").toString()
            precio = datoshab?.getString("Precio").toString()
            foto = datoshab?.getInt("foto")

        }
        tv_tipo.text = Tipo
        tv_descripcion.text = Descripcion
        tv_precio.text = precio
        img_habitacion.setImageResource(foto)
    }
}