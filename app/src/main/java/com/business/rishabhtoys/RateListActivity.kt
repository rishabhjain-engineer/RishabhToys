package com.business.rishabhtoys

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_rate_list.*


class RateListActivity : BaseActivity() {

    private var receivedPurchaseId: Long? = null
    private lateinit var mAdapter : RateListAdapter
    private lateinit var mLayoutManager : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_list)
        if (intent != null) {
            receivedPurchaseId = intent.getLongExtra("purchaseId", 0)
        }

        val repository = Repository(this.application)
        mLayoutManager = LinearLayoutManager(this)
        mAdapter = RateListAdapter()
        rate_list_rv.layoutManager = mLayoutManager
        rate_list_rv.adapter = mAdapter
        rate_list_rv.setHasFixedSize(true)

        add_item_iv.setOnClickListener {
            if (receivedPurchaseId != null) {
                showAddItemDialog()
            }
        }

        repository.getRateList(receivedPurchaseId!!)
            ?.observe(this, object : Observer<List<RateListEntity>> {
                override fun onChanged(t: List<RateListEntity>?) {
                    if(t != null) {
                        mAdapter.setDataSet(t)
                        mAdapter.notifyDataSetChanged()
                    }
                }

            })

    }

    private fun showAddItemDialog() {
        val fm: FragmentManager = supportFragmentManager
        val addItemDialogFragment: AddItemDialogFragment =
            AddItemDialogFragment.getAddDialogFragment(receivedPurchaseId!!)
        addItemDialogFragment.isCancelable = true
        addItemDialogFragment.show(fm, "fragment_add_item")
    }
}
