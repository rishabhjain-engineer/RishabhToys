package com.example.rishabhtoys

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

/**
 * A simple [Fragment] subclass.
 */
class BuyerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buyer, container, false)
    }

    override fun onResume() {
        super.onResume()

        val repository = Repository(activity!!.application)
        repository.getAllEntities()?.observe(this, object  : Observer<List<Entity>>{
            override fun onChanged(t: List<Entity>?) {

                Log.e("Rishabh","list size: "+t?.size)
                Log.e("Rishabh","received gst no: "+t?.get(0)?.gstNo )
            }

        })
    }

}
