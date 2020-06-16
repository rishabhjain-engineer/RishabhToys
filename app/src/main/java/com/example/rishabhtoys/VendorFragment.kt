package com.example.rishabhtoys

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_buyer.*
import kotlinx.android.synthetic.main.fragment_vendor.*

/**
 * A simple [Fragment] subclass.
 */
class VendorFragment : Fragment() {

    lateinit var layoutManager : LinearLayoutManager
    var mSaleList : List<Entity> = ArrayList()
    lateinit var mBuyerFragmentAdapter: BuyerFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vendor, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        layoutManager = LinearLayoutManager(activity)
        sale_recyclerview.layoutManager = layoutManager
        sale_recyclerview.setHasFixedSize(true)
        mBuyerFragmentAdapter = BuyerFragmentAdapter()
        sale_recyclerview.adapter = mBuyerFragmentAdapter

        val repository = Repository(activity!!.application)
        repository.getSaleEntities()?.observe(activity!! , Observer<List<Entity>> {
            if(it != null && it.isNotEmpty()) {
                Log.e("Rishabh","sale size: "+it.size)
                mSaleList = it
                mBuyerFragmentAdapter.setData(mSaleList)
                mBuyerFragmentAdapter.notifyDataSetChanged()
            }
        })
    }
}
