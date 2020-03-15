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
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    var correorec = ""
    var passwordrec = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        bt_register.setOnClickListener{
            var intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        tv_recuperar.setOnClickListener{
            val auth = FirebaseAuth.getInstance()
            val correorec = et_correo.text.toString()
            val emailAddress = "user@example.com"
            if(!(correorec.isEmpty()) && isEmailValid(correorec)){
                auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Revisa tu correo", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this, "Correo no registrado, registrate", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            else{
                Toast.makeText(this, "Correo no válido", Toast.LENGTH_SHORT).show()
            }
        }

        bt_login.setOnClickListener{
            val auth = FirebaseAuth.getInstance()

            val correomio = et_correo.text.toString()
            val passwordmio = et_password.text.toString()

            if(!(correomio.isEmpty()) && !(passwordmio.isEmpty()) && isEmailValid(correomio)){
                auth.signInWithEmailAndPassword(correomio, passwordmio)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("LoginActivity1", "signInWithEmail:success")
                            // val user = auth.currentUser
                            goToMainActicity()
                        } else {
                            Log.w("LoginActivity1", "signInWithEmail:failure", task.exception)
                            if(task.exception!!.message.equals(getString(R.string.error_msg_login_conection))){
                                Toast.makeText(this, "Verifique su conexión a Internet", Toast.LENGTH_SHORT).show()
                            }
                            else if(task.exception!!.message.equals(getString(R.string.error_msg_login))){
                                Toast.makeText(this, "Usuario no registrado, Registrate", Toast.LENGTH_SHORT).show()
                            }
                            else if(task.exception!!.message.equals(getString(R.string.error_msg_incorrect_pass))){
                                Toast.makeText(this, "Contraseña Incorrecta", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            }
            else{
                Toast.makeText(this, "Correo no válido", Toast.LENGTH_SHORT).show()
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
        startActivity(intentmain)
        finish()
    }


}
fun isEmailValid(email: String?): Boolean {
    val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
    val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher: Matcher = pattern.matcher(email)
    return matcher.matches()
}
