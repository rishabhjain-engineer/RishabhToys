package com.example.rishabhtoys

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_vendor.*

/**
 * A simple [Fragment] subclass.
 */
class VendorFragment : Fragment(), SendEntityObject {

    lateinit var layoutManager: LinearLayoutManager
    var mSaleList: List<EntityTransData> = ArrayList()
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
        mBuyerFragmentAdapter = BuyerFragmentAdapter(this)
        sale_recyclerview.adapter = mBuyerFragmentAdapter

        val repository = Repository(activity!!.application)
        repository.getSaleEntity()

        repository.mSaleEntities?.observe(activity!!, Observer<List<EntityTransData>> {
            if (it != null && it.isNotEmpty()) {
                mSaleList = it
                mBuyerFragmentAdapter.setData(mSaleList)
                mBuyerFragmentAdapter.notifyDataSetChanged()
            }
        })
    }


    override fun sendEntity(entityTransData: EntityTransData) {
        val intent = Intent(activity, DetailEntityActivity::class.java)
        intent.putExtra("entityId", entityTransData.id)
        startActivity(intent)
    }
}
