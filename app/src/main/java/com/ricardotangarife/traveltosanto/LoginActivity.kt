package com.ricardotangarife.traveltosanto

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ricardotangarife.traveltosanto.utils.botton_navegation.InicioActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {

    var correorec = ""
    var passwordrec = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_registro.setOnClickListener{
            var intent = Intent(this, RegistroActivity::class.java)
            startActivityForResult(intent, 1234)
        }

        bt_login.setOnClickListener{
            val correomio = et_correo.text.toString()
            val passwordmio = et_password.text.toString()
            if(correomio==correorec && correorec!=""){
                if(passwordmio==passwordrec){
                    var intentmain = Intent(this, InicioActivity::class.java)
                    intentmain.putExtra("correo",correorec)
                    intentmain.putExtra("password",passwordrec)
                    startActivity(intentmain)
                    finish()
                }
                else{
                    Toast.makeText(this, "Contraseña Incorrecta", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                if(passwordmio==passwordrec){
                    Toast.makeText(this, "Correo Incorrecto", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, "Correo y Contraseña Incorrecta", Toast.LENGTH_SHORT).show()
                }
            }
        }


        var datosMain = intent.extras
        if(datosMain != null){
          //  Toast.makeText(this, datosMain?.getString("correo"), Toast.LENGTH_SHORT).show()
           // Toast.makeText(this,datosMain?.getString("password"), Toast.LENGTH_SHORT).show()
            correorec = datosMain?.getString("correo").toString()
            passwordrec = datosMain?.getString("password").toString()
        }
        else{
        //    Toast.makeText(this,"Vacioooo", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==1234 && resultCode== Activity.RESULT_CANCELED){
           // Toast.makeText(this, "No envio nada", Toast.LENGTH_SHORT).show()
        }
        if(requestCode==1234 && resultCode== Activity.RESULT_OK){
       //     Toast.makeText(this, data?.extras?.getString("correo"), Toast.LENGTH_SHORT).show()
        //    Toast.makeText(this, data?.extras?.getString("password"), Toast.LENGTH_SHORT).show()
            correorec = data?.extras?.getString("correo").toString()
            passwordrec = data?.extras?.getString("password").toString()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}
