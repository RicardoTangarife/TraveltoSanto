package com.ricardotangarife.traveltosanto

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
//import com.ricardotangarife.traveltosanto.utils.Constantes.Companion.EMPTY
//import com.ricardotangarife.traveltosanto.utils.Constantes.Companion.INTERLINE
//import com.ricardotangarife.traveltosanto.utils.Constantes.Companion.SPACE
import kotlinx.android.synthetic.main.activity_registro.*
import java.text.SimpleDateFormat
import java.util.*

class RegistroActivity : AppCompatActivity() {

    private var cal = Calendar.getInstance()
    private lateinit var fecha : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        bt_resgistrar.setOnClickListener {
            Log.d( "button", "click")
            val nombre = et_nombre.text.toString()
            val correo = et_correo.text.toString()
            val password = et_password.text.toString()
            val repPassword = et_reppassword.text.toString()
            val size = password.length


            if (nombre.isEmpty() || correo.isEmpty() || et_telefono.text.toString().isEmpty()||password.isEmpty()){
                Toast.makeText( this, "Debe digitar todos los campos", Toast.LENGTH_SHORT).show()
            }
            else{
                if(repPassword == password && size>=6)
                {
                    var intent = Intent()
                    intent.putExtra("correo",correo)
                    intent.putExtra("password",password)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
                else{
                    if (size>6)
                    {
                        Toast.makeText(this,"Contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this,"Contraseña debe tener minimo 6 caracteres", Toast.LENGTH_SHORT).show()

                    }
                }

            }
        }

        bt_cancelar.setOnClickListener{

            onBackPressed()
        }
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED)
        finish()
        super.onBackPressed()
    }


}