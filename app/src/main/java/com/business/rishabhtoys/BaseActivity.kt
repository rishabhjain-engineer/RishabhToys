package com.business.rishabhtoys

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity(){

    private var listener : DialogActionCallback? = null
    var sharePref: SharePref? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharePref = SharePref.getSharePrefInstance(this)
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
            if(listener != null){
                listener?.sendCallback(true)
            }
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

    open fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }

}