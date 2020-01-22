package com.ricardotangarife.traveltosanto

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import com.ricardotangarife.traveltosanto.utils.Constantes.Companion.EMPTY
import com.ricardotangarife.traveltosanto.utils.Constantes.Companion.INTERLINE
import com.ricardotangarife.traveltosanto.utils.Constantes.Companion.SPACE
import kotlinx.android.synthetic.main.activity_registro.*
import java.text.SimpleDateFormat
import java.util.*

class RegistroActivity : AppCompatActivity() {

    private var cal = Calendar.getInstance()
    private lateinit var fecha : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
    /*    var sexo = getString(R.string.masculino)

        rb_masculino.setOnClickListener {
            sexo  = getString(R.string.masculino)
        }
        rb_femenino.setOnClickListener {
            sexo  = getString(R.string.femenino)
        }
*/
        val dateSetListener  = object : DatePickerDialog.OnDateSetListener{
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val format = "MM/dd/yyyy"
                val sdf = SimpleDateFormat(format, Locale.US)
                fecha = sdf.format(cal.time).toString()
                /*Log.d("Fecha", fecha)*/
                tv_showPicker.text = fecha
            }

        }

        tv_showPicker.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        bt_resgistrar.setOnClickListener {
            Log.d( "button", "click")
            val nombre = et_nombre.text.toString()
            val correo = et_correo.text.toString()
            val password = et_password.text.toString()
            val repPassword = et_password.text.toString()
            var pasatiempos = EMPTY

            if(cb_cine.isChecked) pasatiempos = pasatiempos + SPACE + getString(R.string.cine)
            if(cb_gimnasio.isChecked) pasatiempos = pasatiempos + SPACE + getString(R.string.gimnasio)
            if(cb_leer.isChecked) pasatiempos = pasatiempos + SPACE + getString(R.string.leer)
            if(cb_series.isChecked) pasatiempos = pasatiempos + SPACE + getString(R.string.series)

            /*if(rb_masculino.isChecked) sexo = "Masculino"
            else sexo = "Femenino"*/

            /*
            if (nombre.isEmpty() || correo.isEmpty() || et_telefono.text.toString().isEmpty()||password.isEmpty()){
                Toast.makeText( this, "Debe digitar todos los campos", Toast.LENGTH_SHORT).show()
            }else{
                val telefono = et_telefono.text.toString().toInt()
                tv_resultado.text = getString(R.string.nombre_lb)+ SPACE + nombre +
                        INTERLINE + getString(R.string.correo_lb)+ SPACE + correo +
                        INTERLINE + getString(R.string.telefono_lb)+ SPACE + telefono +
                        INTERLINE + getString(R.string.contrasena_lb)+ SPACE + password +
  //                      INTERLINE + getString(R.string.sexo_lb)+ SPACE + sexo +
                        INTERLINE + getString(R.string.pasatiempo_lb)+ SPACE + pasatiempos +
                        INTERLINE + getString(R.string.fecha_nacimiento) + SPACE + fecha
            }

*/
        }
    }


}