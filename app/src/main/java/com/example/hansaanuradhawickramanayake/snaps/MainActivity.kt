package com.example.hansaanuradhawickramanayake.snaps

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.widget.Toast
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import android.R.attr.password
import android.support.v4.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.R.attr.password




class MainActivity : AppCompatActivity() {


    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance();
    }

    fun signIn(view: View){

        if (emailEditText.text.isEmpty() || passwordEditText.text.isEmpty()){

            Toast.makeText(this@MainActivity, "Enter Email and Password", Toast.LENGTH_SHORT).show()
        } else {
            mAuth?.signInWithEmailAndPassword(emailEditText.text.toString(), passwordEditText.text.toString())
                    ?.addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("result", "signInWithEmail:success")
                            Toast.makeText(this@MainActivity, "Signed In Successfully", Toast.LENGTH_SHORT).show()
                            moveToSnapsActivity()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("result", "signInWithEmail:failure", task.exception)
                            Toast.makeText(this@MainActivity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }

                        // ...
                    }
        }
    }

    fun signUp(view: View){

        if (emailEditText.text.isEmpty() || passwordEditText.text.isEmpty()){

            Toast.makeText(this@MainActivity, "Enter Email and Password", Toast.LENGTH_SHORT).show()
        } else {
            mAuth?.createUserWithEmailAndPassword(emailEditText.text.toString(), passwordEditText.text.toString())
                    ?.addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("result", "createUserWithEmail:success")
                            Toast.makeText(this@MainActivity, "Signed Up Successfully", Toast.LENGTH_SHORT).show()
                            moveToSnapsActivity()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("result", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(this@MainActivity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }

                        // ...
                    }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth?.getCurrentUser()

        if(currentUser != null){

            moveToSnapsActivity()

        }
    }

    fun moveToSnapsActivity(){

        val intent = Intent(this, SnapsActivity::class.java)
        startActivity(intent)
        finish()

    }
}
