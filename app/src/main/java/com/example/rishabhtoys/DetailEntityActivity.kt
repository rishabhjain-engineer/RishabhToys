package com.example.rishabhtoys

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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

        d_e_rate_list_iv.setOnClickListener {
            val intent = Intent(this , RateListActivity::class.java)
            intent.putExtra("purchaseId",receivedEntityId)
            startActivity(intent)
        }

        d_e_edit.setOnClickListener {
            val intent = Intent(this , UpdateEntityActivity::class.java)
            intent.putExtra("entityId",receivedEntityId)
            startActivity(intent)
        }

        mViewModel.getData()?.observe(this, Observer<List<DetailInfoForEntity>> {

            if (it.isNotEmpty()) {
                d_e_company_name.text = it[0].entity.companyName
                d_e_company_address.text = it[0].entity.companyAddress
                d_e_gst.text = it[0].entity.gstNo
                val entityType = it[0].entity.entityType
                if (0 == entityType) {
                    d_e_entity_type.text = Purchase
                    d_e_rate_list_iv.visibility = View.VISIBLE
                }else if(1 == entityType){
                    d_e_entity_type.text = Sale
                    d_e_rate_list_iv.visibility = View.GONE
                }else{
                    d_e_entity_type.text = "UnKnown"
                }
                d_e_company_owner.text = it[0].entity.companyOwner
                d_e_primary_no.text = it[0].entity.primaryContactNo
                d_e_alt_no.text = it[0].entity.altContactNo
                d_e_rating.rating = it[0].entity.entityRating!!
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
