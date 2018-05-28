package com.example.hansaanuradhawickramanayake.snaps

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_view_snap.*

class ViewSnapActivity : AppCompatActivity() {

    // Firebase
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_snap)

        mAuth = FirebaseAuth.getInstance()


        Picasso.get().load(intent.getStringExtra("imageURL")).into(snapImageView)
        messageTextView.text = intent.getStringExtra("message")
    }

    override fun onBackPressed() {
        super.onBackPressed()

        FirebaseDatabase.getInstance().getReference().child("users").child(mAuth?.currentUser?.uid).child("snaps").child(intent.getStringExtra("snapKey")).removeValue()

        FirebaseStorage.getInstance().getReference().child("Images").child(intent.getStringExtra("imageName")).delete()

    }
}
