package com.business.rishabhtoys

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_buyer.*

/**
 * A simple [Fragment] subclass.
 */
class BuyerFragment : Fragment(), SendEntityObject {

    lateinit var layoutManager: LinearLayoutManager
    var mPurchaseList: List<EntityTransData> = ArrayList()
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

        mBuyerFragmentAdapter = BuyerFragmentAdapter(this)
        purchase_recyclerview.adapter = mBuyerFragmentAdapter

        val repository = Repository(activity!!.application)
        repository.getPurchaseEntity()

       /* repository.getAllEntities()?.observe(activity!!, Observer<List<Entity>> {
            if (it != null && it.isNotEmpty()) {
            }
        })*/

        repository.mPurchaseEntities?.observe(activity!!, Observer<List<EntityTransData>> {
            if (it != null && it.isNotEmpty()) {
                mPurchaseList = it
                mBuyerFragmentAdapter.setData(mPurchaseList)
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
