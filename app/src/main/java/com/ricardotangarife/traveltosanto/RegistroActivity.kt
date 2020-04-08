package com.ricardotangarife.traveltosanto

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.ricardotangarife.traveltosanto.model.User
import com.ricardotangarife.traveltosanto.model.room.Reserva
//import com.ricardotangarife.traveltosanto.utils.Constantes.Companion.EMPTY
//import com.ricardotangarife.traveltosanto.utils.Constantes.Companion.INTERLINE
//import com.ricardotangarife.traveltosanto.utils.Constantes.Companion.SPACE
import kotlinx.android.synthetic.main.activity_registro.*
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

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
            val telefono = et_telefono.text.toString()
            val size = password.length

            val auth = FirebaseAuth.getInstance()

            if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty()||password.isEmpty()){
                Toast.makeText( this, "Debe digitar todos los campos", Toast.LENGTH_SHORT).show()
            }
            else{
                if( isEmailValid(correo)){
                    if(repPassword == password && size>=6){
                        auth.createUserWithEmailAndPassword(correo, password)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("LoginActivity1", "signInWithEmail:success")
                                    val user = auth.currentUser
                                    createUserDatabase(user,nombre,telefono)
                                    /*crearUsuarioRoom(nombre, correo)*/
                                    goToLoginActicity()
                                    Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                                } else {
                                    Log.w("LoginActivity1", "signInWithEmail:failure", task.exception)
                                    if(task.exception!!.message.equals(getString(R.string.error_msg_email_used))){
                                        Toast.makeText(this, "El correo ya está registrado", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                    }
                    else{
                        if (size>6){
                            Toast.makeText(this,"Contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(this,"Contraseña debe tener minimo 6 caracteres", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else{
                    Toast.makeText(this, "Correo no válido", Toast.LENGTH_SHORT).show()
                }
            }
        }
        bt_cancelar.setOnClickListener{
            onBackPressed()
        }
    }

    private fun createUserDatabase(user: FirebaseUser?, name: String, movil: String){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("usuarios")
        val usuario = User(user!!.uid, user!!.email.toString(), name,movil)
        myRef.child(user!!.uid).setValue(usuario)
    }

    /*private fun crearUsuarioRoom(nombre: String, correo: String){
        val usuario = Usuario("id", nombre, "drawable")
        val usuarioDao = SesionRoom.database.UsuarioDAO()
        usuarioDao.insertUsuario(usuario)
    }*/
    private fun goToLoginActicity(){
        var intentlogin = Intent(this, LoginActivity::class.java)
        startActivity(intentlogin)
        finish()
    }
    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED)
        finish()
        super.onBackPressed()
    }
    fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }


}