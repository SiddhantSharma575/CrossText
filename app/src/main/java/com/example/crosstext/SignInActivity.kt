package com.example.crosstext

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SignInActivity : AppCompatActivity() {

    lateinit var signInBtn:Button
    private lateinit var auth:FirebaseAuth
    private lateinit var signInClient: GoogleSignInClient

    companion object{
        private const val RC_SIGN_IN = 120
        private const val TAG = "SignInActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        signInBtn = findViewById(R.id.btnGoogleSignIn)
        signInBtn.setBackgroundResource(R.drawable.btnbg)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("180121014215-39f0g8o38p7hckbt215dmi7ttd0k94oi.apps.googleusercontent.com")
            .requestEmail()
            .build()

        signInClient = GoogleSignIn.getClient(this, gso)
        auth = FirebaseAuth.getInstance()

        signInBtn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = signInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Failed To Sign In", Toast.LENGTH_SHORT).show()
                }
            }
    }
}