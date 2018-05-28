package com.example.hansaanuradhawickramanayake.snaps

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_choose_user.*
import kotlinx.android.synthetic.main.activity_snaps.*
import kotlinx.android.synthetic.main.activity_view_snap.*


class SnapsActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    var emails: ArrayList<String> = ArrayList()
    var snaps: ArrayList<DataSnapshot> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snaps)

        mAuth = FirebaseAuth.getInstance()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, emails)
        snapsListView.adapter = adapter


        FirebaseDatabase.getInstance().reference.child("users").child(mAuth?.currentUser?.uid).child("snaps").addChildEventListener(object : ChildEventListener{

            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {

                emails.add(p0?.child("from")?.value as String)
                snaps.add(p0)
                adapter.notifyDataSetChanged()

            }


            override fun onCancelled(p0: DatabaseError?) {}
            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}
            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {}
            override fun onChildRemoved(p0: DataSnapshot?) {}

        })

        snapsListView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            val snapshot = snaps.get(position)

            val message: String = snapshot.child("message").value as String
            val imageURL: String = snapshot.child("imageURL").value as String

            val intent = Intent(this, ViewSnapActivity::class.java)
            intent.putExtra("message", message)
            intent.putExtra("imageURL", imageURL)
            startActivity(intent)


        }



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
