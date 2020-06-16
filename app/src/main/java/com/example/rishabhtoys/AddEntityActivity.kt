package com.example.rishabhtoys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_add_entity.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddEntityActivity : AppCompatActivity() {

    private lateinit var viewModel: AddEntityViewModel
    private var receivedEntityType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entity)

        viewModel = ViewModelProvider(this).get(AddEntityViewModel::class.java)

        if (intent != null) {
            receivedEntityType = intent.getStringExtra(Entity_Type)
        }


        create_account.setOnClickListener {
            GlobalScope.launch {
                viewModel.insertEntity(createEntity())
            }
            finish()
        }

    }

    private fun createEntity(): Entity {

        val entity = Entity()
        entity.companyName = addEntity_firm_name_et.text.toString()
        entity.companyOwner = addEntity_owner_name_et.text.toString()
        entity.companyAddress = addEntity_address_et.text.toString()
        entity.primaryContactNo = addEntity_contact_primary_et.text.toString()
        entity.altContactNo = addEntity_contact_alt_et.text.toString()
        entity.gstNo = addEntity_gst_et.text.toString()
        entity.amount = addEntity_amount_et.text.toString().toInt()

        if (receivedEntityType != null && Purchase.equals(receivedEntityType)) {
            entity.entityType = Purchase
            entity.txnType = Debit // We need to pay the amount
        } else if (receivedEntityType != null && Sale.equals(receivedEntityType)) {
            entity.entityType = Sale
            entity.txnType = Credit // We need to receive the amount
        }
        entity.txnDateTime = Utils.getTxnDateTime()
        return entity
    }

}
