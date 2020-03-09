package com.ricardotangarife.traveltosanto

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
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
            val auth = FirebaseAuth.getInstance()

            val correomio = et_correo.text.toString()
            val passwordmio = et_password.text.toString()

            if(!(correomio.isEmpty()) && !(passwordmio.isEmpty())){
                auth.signInWithEmailAndPassword(correomio, passwordmio)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("LoginActivity1", "signInWithEmail:success")
                            // val user = auth.currentUser
                            goToMainActicity()
                        } else {
                            Log.w("LoginActivity1", "signInWithEmail:failure", task.exception)
                            if(task.exception!!.message.equals(getString(R.string.error_msg_email_login))){
                                Toast.makeText(this, "Correo no válido", Toast.LENGTH_SHORT).show()
                            }
                            else if(task.exception!!.message.equals(getString(R.string.error_msg_login))){
                                Toast.makeText(this, "Usuario no registrado, Registrate", Toast.LENGTH_SHORT).show()
                            }
                        }
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

    public override fun onStart() {
        super.onStart()
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if(currentUser != null){
            goToMainActicity()
        }
    }

    private fun goToMainActicity() {
        var intentmain = Intent(this, InicioActivity::class.java)
        //intentmain.putExtra("correo",correorec)
        //intentmain.putExtra("password",passwordrec)
        startActivity(intentmain)
        finish()
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
