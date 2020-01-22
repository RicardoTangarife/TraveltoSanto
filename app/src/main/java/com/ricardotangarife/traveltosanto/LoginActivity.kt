package com.ricardotangarife.traveltosanto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_registro.setOnClickListener{
            var intent = Intent(this, RegistroActivity::class.java)
            startActivityForResult(intent, 1234)
        }
        
    }


}
