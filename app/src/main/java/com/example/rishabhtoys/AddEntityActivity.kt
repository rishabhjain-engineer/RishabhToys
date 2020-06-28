package com.example.rishabhtoys

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_add_entity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class AddEntityActivity : BaseActivity(), DialogActionCallback{

    private lateinit var viewModel: AddEntityViewModel
    // 0 for Purchase : Debit
    // 1 for Sale : Credit
    private var receivedEntityType: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entity)

        viewModel = ViewModelProvider(this).get(AddEntityViewModel::class.java)
        setListener(this)

        if (intent != null) {
            receivedEntityType = intent.getIntExtra(Entity_Type, 0)
        }


        create_account.setOnClickListener {

            if (validateUI()) {
                val result = GlobalScope.async {
                    viewModel.createEntityAndInsertTxnHistory(createEntity(), createTxnHistory())
                }
                GlobalScope.launch (Dispatchers.Main){
                    val status: Long? = result.await()
                    val ret: Int? = status?.compareTo(0)
                    if (ret != null && ret >= 0) {
                        showDialog(
                            "Success",
                            "Account created successfully !!",
                            R.drawable.ic_check_circle_24dp
                        )
                    } else {
                        showDialog(
                            "Error",
                            "Account couldn't be created !!",
                            R.drawable.ic_alert_error_24dp
                        )
                    }
                }
            }


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
        entity.totalAmount = addEntity_amount_et.text.toString().toFloat()

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

    private fun createTxnHistory(): TxnHistoryEntity {
        val txnHistoryEntity = TxnHistoryEntity()
        txnHistoryEntity.date = Utils.getTxnDateTime()
        txnHistoryEntity.remark = "Creating account."
        txnHistoryEntity.txnAmount = addEntity_amount_et.text.toString().toFloat()
        txnHistoryEntity.txnType = 0

        return txnHistoryEntity
    }

    fun validateUI(): Boolean {

        if (TextUtils.isEmpty(addEntity_firm_name_et.text.toString())) {
            showDialog("Error", "Please enter company name !!", R.drawable.ic_alert_error_24dp)
        } else if (TextUtils.isEmpty(addEntity_owner_name_et.text.toString())) {
            showDialog("Error", "Please enter owner's name !!", R.drawable.ic_alert_error_24dp)
        } else if (TextUtils.isEmpty(addEntity_address_et.text.toString())) {
            showDialog("Error", "Please enter company address !!", R.drawable.ic_alert_error_24dp)
        } else if (TextUtils.isEmpty(addEntity_contact_primary_et.text.toString())) {
            showDialog(
                "Error",
                "Please enter primary contact no !!",
                R.drawable.ic_alert_error_24dp
            )
        } else if (addEntity_contact_primary_et.text.toString().length != 10) {
            showDialog(
                "Error",
                "Please enter valid primary contact no !!",
                R.drawable.ic_alert_error_24dp
            )
        } else if (!TextUtils.isEmpty(addEntity_contact_alt_et.text.toString()) && addEntity_contact_alt_et.text.toString().length != 10) {
            showDialog(
                "Error",
                "Please enter valid alternate contact no !!",
                R.drawable.ic_alert_error_24dp
            )
        } else if (TextUtils.isEmpty(addEntity_amount_et.editableText.toString())) {
            showDialog(
                "Error",
                "Please enter opening balance amount !!",
                R.drawable.ic_alert_error_24dp
            )
        } else {
            return true
        }

        return false
    }

    override fun sendCallback(positiveAction: Boolean) {
        if(positiveAction){
            finish()
        }
    }

}
