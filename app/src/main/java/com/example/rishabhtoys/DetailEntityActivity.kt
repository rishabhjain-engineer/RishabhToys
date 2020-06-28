package com.example.rishabhtoys

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_detail_entity.*

class DetailEntityActivity : BaseActivity() {

    private var receivedEntityId: Long? = 0
    private var entity: Entity? = null
    private lateinit var mViewModel: DetailEntityViewModel
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: TxnHistoryAdapter
    private var mTxnHistoryList : MutableList<TxnHistoryEntity> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_entity)

        if (intent != null) {
            receivedEntityId = intent.getLongExtra("entityId", 0)
        }

        mLayoutManager = LinearLayoutManager(this)
        txn_history_rv.layoutManager = mLayoutManager
        mAdapter = TxnHistoryAdapter()
        txn_history_rv.adapter = mAdapter

        mViewModel = ViewModelProvider(this).get(DetailEntityViewModel::class.java)
        if (receivedEntityId != null) {
            mViewModel.getDetailEntityInfo(receivedEntityId)
        }

        mViewModel.getData()?.observe(this, Observer<List<DetailInfoForEntity>> {

            if (it.isNotEmpty()) {
                d_e_company_name.text = it[0].entity.companyName
                d_e_company_address.text = it[0].entity.companyAddress
                d_e_gst.text = it[0].entity.gstNo
                d_e_entity_type.text = it[0].entity.entityType.toString()
                d_e_company_owner.text = it[0].entity.companyOwner
                d_e_primary_no.text = it[0].entity.primaryContactNo
                d_e_alt_no.text = it[0].entity.altContactNo
            }

            mTxnHistoryList.clear()
            it.forEach {
                mTxnHistoryList.add(it.txnHistoryEntity)
                mAdapter.setData(mTxnHistoryList)
                mAdapter.notifyDataSetChanged()
            }

        })
    }
}
