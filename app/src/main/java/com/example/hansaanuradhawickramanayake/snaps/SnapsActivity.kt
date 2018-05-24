package com.example.hansaanuradhawickramanayake.snaps

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth



class SnapsActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snaps)

        mAuth = FirebaseAuth.getInstance();

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {


        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.logout){
            signOut()
        } else if (item?.itemId == R.id.createSnap){

            val intent = Intent(this, CreateSnapActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    fun signOut(){

        mAuth?.signOut()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
