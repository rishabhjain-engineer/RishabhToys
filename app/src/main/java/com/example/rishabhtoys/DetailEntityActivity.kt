package com.example.rishabhtoys

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_detail_entity.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailEntityActivity : BaseActivity() {

    private var receivedCompanyName: String? = ""
    private var entity: Entity? = null
    private lateinit var mViewModel: DetailEntityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_entity)

        if (intent != null) {
            receivedCompanyName = intent.getStringExtra("companyName")
        }

        val repository = Repository(this.application)
        mViewModel = ViewModelProvider(this).get(DetailEntityViewModel::class.java)


        val result = GlobalScope.async {
            repository.getEntity(receivedCompanyName)
        }

        GlobalScope.launch {
            val entity: Entity? = result.await()
            d_e_company_name.text = entity?.companyName
            d_e_company_address.text = entity?.companyAddress
            d_e_gst.text = entity?.gstNo
            d_e_company_owner.text = entity?.companyOwner
            d_e_primary_no.text = entity?.primaryContactNo
            d_e_alt_no.text = entity?.altContactNo
        }

    }
}
