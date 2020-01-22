package com.ricardotangarife.traveltosanto

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

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
            if(correomio==correorec){
                if(passwordmio==passwordrec){
                    var intentmain = Intent(this, MainActivity::class.java)
                    intent.putExtra("correo",correorec)
                    intent.putExtra("password",passwordrec)
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==1234 && resultCode== Activity.RESULT_CANCELED){
            Toast.makeText(this, "No envio nada", Toast.LENGTH_SHORT).show()
        }
        if(requestCode==1234 && resultCode== Activity.RESULT_OK){
            Toast.makeText(this, data?.extras?.getString("correo"), Toast.LENGTH_SHORT).show()
            Toast.makeText(this, data?.extras?.getString("password"), Toast.LENGTH_SHORT).show()
            correorec = data?.extras?.getString("correo").toString()
            passwordrec = data?.extras?.getString("password").toString()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}
