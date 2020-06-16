package com.example.rishabhtoys

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_add_entity.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddEntityActivity : AppCompatActivity() {

    private lateinit var viewModel: AddEntityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entity)

        viewModel = ViewModelProvider(this).get(AddEntityViewModel::class.java)

        create_account.setOnClickListener {
            GlobalScope.launch {
                viewModel.insertEntity(createEntity())
                Log.e("Rishabh","post insertion")
            }
            //finish()
        }

    }

    private fun createEntity(): Entity {

        Log.e("Rishabh", "Filling up values")
        val entity = Entity()
        entity.companyName = addEntity_firm_name_et.text.toString()
        entity.companyOwner = addEntity_owner_name_et.text.toString()
        entity.companyAddress = addEntity_address_et.text.toString()
        entity.primaryContactNo = addEntity_contact_primary_et.text.toString()
        entity.altContactNo = addEntity_contact_alt_et.text.toString()
        entity.gstNo = addEntity_gst_et.text.toString()
        entity.amount = addEntity_amount_et.text.toString().toInt()
        entity.entityType = Purchase
        entity.txnType = Credit
        Log.e("Rishabh", "date time: " + Utils.getTxnDateTime())
        entity.txnDateTime = Utils.getTxnDateTime()
        Log.e("Rishabh", "returning entity object")
        return entity
    }

}
