package com.example.hansaanuradhawickramanayake.snaps

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_view_snap.*

class ViewSnapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_snap)

        val meesage = intent.getStringExtra("message")
        val imageURL = intent.getStringExtra("imageURL")

        Picasso.get().load(imageURL).into(snapImageView)
        messageTextView.text = meesage
    }
}
