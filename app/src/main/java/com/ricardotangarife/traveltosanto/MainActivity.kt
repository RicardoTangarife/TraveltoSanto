package com.ricardotangarife.traveltosanto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    var correo = ""
    var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var datosRecibidos = intent.extras
        if(datosRecibidos != null){
            Toast.makeText(this, datosRecibidos?.getString("correo"), Toast.LENGTH_SHORT).show()
            Toast.makeText(this,datosRecibidos?.getString("password"), Toast.LENGTH_SHORT).show()
            correo = datosRecibidos?.getString("correo").toString()
            password = datosRecibidos?.getString("password").toString()
            tv_principal.text = correo
        }
        else{
            Toast.makeText(this,"Vacioooo", Toast.LENGTH_SHORT).show()
            tv_principal.text = "hola vacio"
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_overflow, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_abrir){
            //Toast.makeText(this, "Menu", Toast.LENGTH_SHORT).show()
            var intentcerrar = Intent(this, LoginActivity::class.java)
            intentcerrar.putExtra("correo",correo)
            intentcerrar.putExtra("password",password)
            startActivity(intentcerrar)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


}
