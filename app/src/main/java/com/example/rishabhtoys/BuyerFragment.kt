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

/**
 * A simple [Fragment] subclass.
 */
class BuyerFragment : Fragment() {

    lateinit var layoutManager : LinearLayoutManager
    var mPurchaseList : List<Entity> = ArrayList()
    lateinit var mBuyerFragmentAdapter: BuyerFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buyer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        layoutManager = LinearLayoutManager(activity)
        purchase_recyclerview.layoutManager = layoutManager
        purchase_recyclerview.setHasFixedSize(true)

        mBuyerFragmentAdapter = BuyerFragmentAdapter()
        purchase_recyclerview.adapter = mBuyerFragmentAdapter

        val repository = Repository(activity!!.application)
        repository.getPurchaseEntities()?.observe(activity!! , Observer<List<Entity>> {
            if(it != null && it.isNotEmpty()) {
                Log.e("Rishabh","purchase size: "+it.size)
                mPurchaseList = it
                mBuyerFragmentAdapter.setData(mPurchaseList)
                mBuyerFragmentAdapter.notifyDataSetChanged()
            }
        })

    }

}
