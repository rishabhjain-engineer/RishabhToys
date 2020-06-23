package com.example.rishabhtoys

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_detail_entity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailEntityActivity : BaseActivity() {

    private var receivedEntityId: Int? = 0
    private var entity: Entity? = null
    private lateinit var mViewModel: DetailEntityViewModel
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mAdapter : TxnHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_entity)

        if (intent != null) {
            receivedEntityId = intent.getIntExtra("entityId",0)
        }

        Log.e("Rishabh","received Entity Id: "+receivedEntityId) ;

        mLayoutManager = LinearLayoutManager(this)
        txn_history_rv.layoutManager = mLayoutManager
        mAdapter = TxnHistoryAdapter()
        txn_history_rv.adapter = mAdapter

        mViewModel = ViewModelProvider(this).get(DetailEntityViewModel::class.java)
        if(receivedEntityId != null){
            mViewModel.getDetailEntityInfo(receivedEntityId)
        }

        mViewModel.getData()?.observe(this , Observer<List<DetailInfoForEntity>> {

            Log.e("Rishabh","detail info size: "+it.size)

            it.forEach {
                Log.e("Rishabh","company name: "+it.entity.companyName)
                Log.e("Rishabh","owner name: "+it.entity.companyOwner)
                Log.e("Rishabh","gst no: "+it.entity.gstNo)
                Log.e("Rishabh","primary  no: "+it.entity.primaryContactNo)
                Log.e("Rishabh","total  balance: "+it.entity.totalAmount)
                Log.e("Rishabh","txn  amount: "+it.txnHistoryEntity.txnAmount)
                Log.e("Rishabh","txn  date: "+it.txnHistoryEntity.date)
                Log.e("Rishabh","txn  remark: "+it.txnHistoryEntity.remark)

                Log.e("Rishabh","\n *************************** ")
            }

        })
    }
}
