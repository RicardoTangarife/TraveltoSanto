package com.ricardotangarife.traveltosanto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.ricardotangarife.traveltosanto.utils.botton_navegation.InicioActivity
import kotlinx.android.synthetic.main.activity_login.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var mGoogleApiClient: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleApiClient = GoogleApiClient.Builder(applicationContext)
            .enableAutoManage(this, GoogleApiClient.OnConnectionFailedListener(){
                GoogleApiClient.OnConnectionFailedListener() {
                    Toast.makeText(this, "Error en Login google", Toast.LENGTH_SHORT).show();
                }
            })
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

        // Build a GoogleSignInClient with the options specified by gso.

        bt_google.setOnClickListener{
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent, 101)
        }

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
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 101) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if(result.isSuccess){
                val account = result.signInAccount
                firebaseAuthWithGoogle(account!!)
            }
            else{
                Toast.makeText(this, "Conexión con cuenta Google fallida", Toast.LENGTH_SHORT).show()
            }
            /*val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "Conexión con cuenta Google fallida", Toast.LENGTH_SHORT).show()
                // ...
            }*/

        }
    }
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        // Initialize Firebase Auth
        var auth: FirebaseAuth = FirebaseAuth.getInstance()// ...
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    goToMainActicity()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Conexión con cuenta Google fallida", Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }

}
fun isEmailValid(email: String?): Boolean {
    val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
    val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher: Matcher = pattern.matcher(email)
    return matcher.matches()
}
