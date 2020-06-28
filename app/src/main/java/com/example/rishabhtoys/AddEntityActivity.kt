package com.example.rishabhtoys

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.activity_add_entity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class AddEntityActivity : BaseActivity(), DialogActionCallback {

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

        addEntity_firm_name_et.addTextChangedListener( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s!= null && s.isNotEmpty()){
                    addEntity_firm_name.error = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        addEntity_address_et.addTextChangedListener( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s!= null && s.isNotEmpty()){
                    addEntity_address.error = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        addEntity_owner_name_et.addTextChangedListener( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s!= null && s.isNotEmpty()){
                    addEntity_owner_name.error = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        addEntity_contact_primary_et.addTextChangedListener( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s!= null && s.isNotEmpty()){
                    addEntity_contact_primary.error = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        addEntity_contact_alt_et.addTextChangedListener( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s!= null && s.isNotEmpty()){
                    addEntity_contact_alternate.error = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        create_account.setOnClickListener {

            if (validateUI()) {
                val result = GlobalScope.async {
                    viewModel.createEntityAndInsertTxnHistory(createEntity(), createTxnHistory())
                }
                GlobalScope.launch(Dispatchers.Main) {
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
        if (receivedEntityType != null && 0 == receivedEntityType) {
            txnHistoryEntity.txnType = 0
        } else if (receivedEntityType != null && 1 == receivedEntityType) {
            txnHistoryEntity.txnType = 1
        }

        return txnHistoryEntity
    }

    fun validateUI(): Boolean {

        if (TextUtils.isEmpty(addEntity_firm_name_et.text.toString())) {
            addEntity_firm_name.error = "Please enter company name."
        }  else if (TextUtils.isEmpty(addEntity_address_et.text.toString())) {
            addEntity_address.error = "Please enter company's address."
        } else if (TextUtils.isEmpty(addEntity_owner_name_et.text.toString())) {
            addEntity_owner_name.error = "Please enter owner's name."
        }else if (TextUtils.isEmpty(addEntity_contact_primary_et.text.toString())) {
            addEntity_contact_primary.error = "Please enter primary contact no."
        } else if (addEntity_contact_primary_et.text.toString().length != 10) {
            addEntity_contact_primary.error = "Please enter valid primary contact no."
        } else if (!TextUtils.isEmpty(addEntity_contact_alt_et.text.toString()) && addEntity_contact_alt_et.text.toString().length != 10) {
            addEntity_contact_alternate.error = "Please enter valid alternate contact no."
        } else if (TextUtils.isEmpty(addEntity_amount_et.editableText.toString())) {
            addEntity_amount.error = "Please enter opening amount balance."
        } else {
            return true
        }

        return false
    }

    override fun sendCallback(positiveAction: Boolean) {
        if (positiveAction) {
            finish()
        }
    }

}
