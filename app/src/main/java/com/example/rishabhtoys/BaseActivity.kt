package com.example.rishabhtoys

import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity(){

    private lateinit var listener : DialogActionCallback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun setListener(listener: DialogActionCallback){
        this.listener = listener
    }

    fun showDialog(title:String, message:String, icon:Int){

        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)

        //set message for alert dialog
        builder.setMessage(message)
        builder.setIcon(icon)

        //performing positive action
        builder.setPositiveButton("Ok"){dialogInterface, which ->
            dialogInterface.dismiss()
            listener.sendCallback(true)
        }
        /*//performing cancel action
        builder.setNeutralButton("Cancel"){dialogInterface , which ->
            Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_LONG).show()
        }
        //performing negative action
        builder.setNegativeButton("No"){dialogInterface, which ->
            Toast.makeText(applicationContext,"clicked No",Toast.LENGTH_LONG).show()
        }*/
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}