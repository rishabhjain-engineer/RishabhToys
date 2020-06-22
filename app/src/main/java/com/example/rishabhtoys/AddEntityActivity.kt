package com.example.rishabhtoys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_add_entity.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddEntityActivity : AppCompatActivity() {

    private lateinit var viewModel: AddEntityViewModel
    // 0 for Purchase : Debit
    // 1 for Sale : Credit
    private var receivedEntityType: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entity)

        viewModel = ViewModelProvider(this).get(AddEntityViewModel::class.java)

        if (intent != null) {
            receivedEntityType = intent.getIntExtra(Entity_Type, 0)
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
        entity.amount = addEntity_amount_et.text.toString().toFloat()

        if (receivedEntityType != null && 0 == receivedEntityType) {
            entity.entityType = 0
            entity.txnType = 0 // We need to pay the amount
        } else if (receivedEntityType != null && 1 == receivedEntityType) {
            entity.entityType = 1
            entity.txnType = 1 // We need to receive the amount
        }
        entity.txnDateTime = Utils.getTxnDateTime()
        return entity
    }

}
