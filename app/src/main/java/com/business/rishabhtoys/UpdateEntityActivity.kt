package com.business.rishabhtoys

import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import kotlinx.android.synthetic.main.activity_update_entity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*

class UpdateEntityActivity : BaseActivity(), DialogActionCallback {

    private var receivedId: Long? = null
    private lateinit var companyName: String
    private lateinit var companyAddress: String
    private lateinit var gst: String
    private lateinit var ownerName: String
    private lateinit var primaryContactNo: String
    private lateinit var altContactNo: String
    private var rating: Float = 0F
    private var entityType: Int? = null
    private var creationDate: Date? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_entity)

        if (intent != null) {
            receivedId = intent.getLongExtra("entityId", 0)
        }

        val repository = Repository(this@UpdateEntityActivity.application)
        setListener(this)

        if (receivedId != null) {
            val result = GlobalScope.async {

                repository.getEntityBasicDetail(receivedId!!)
            }

            GlobalScope.launch(Dispatchers.Main) {
                val entity = result.await()

                companyName = entity?.companyName.toString()
                updateEntity_firm_name_et.setText(companyName)

                companyAddress = entity?.companyAddress.toString()
                updateEntity_address_et.setText(companyAddress)

                gst = entity?.gstNo.toString()
                if (!TextUtils.isEmpty(gst)) {
                    updateEntity_gst_et.setText(gst)
                }

                ownerName = entity?.companyOwner.toString()
                updateEntity_owner_name_et.setText(ownerName)

                primaryContactNo = entity?.primaryContactNo.toString()
                updateEntity_contact_primary_et.setText(primaryContactNo)

                altContactNo = entity?.altContactNo.toString()
                if (!TextUtils.isEmpty(altContactNo)) {
                    updateEntity_contact_alt_et.setText(altContactNo)
                }


                updateEntity_amount_et.setText(entity?.totalAmount.toString())
                updateEntity_amount_et.isEnabled = false
                updateEntity_amount_et.inputType = InputType.TYPE_NULL

                rating = entity?.entityRating!!
                updateEntity_rating.rating = rating

                entityType = entity.entityType

                creationDate = entity.txnDateTime
            }
        }

        updateEntity_rating.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            this.rating = rating
        }

        update_account.setOnClickListener {
            if (validateFields()) {
                val result = GlobalScope.async {
                    repository.updateEntity(updateEntity())
                }

                GlobalScope.launch(Dispatchers.Main) {
                    val status: Int? = result.await()
                    if (status != null && status >= 0) {
                        showDialog(
                            "Success",
                            "Update is successfully recorded.",
                            R.drawable.ic_check_circle_24dp
                        )
                    } else {
                        showDialog("Error", "Update is failed.", R.drawable.ic_alert_error_24dp)

                    }
                }
            }

        }

    }

    private fun validateFields(): Boolean {

        if (TextUtils.isEmpty(updateEntity_firm_name_et.text.toString())) {
            updateEntity_firm_name.error = "Please enter company name."
        } else if (TextUtils.isEmpty(updateEntity_address_et.text.toString())) {
            updateEntity_address.error = "Please enter company's address."
        } else if (TextUtils.isEmpty(updateEntity_owner_name_et.text.toString())) {
            updateEntity_owner_name.error = "Please enter owner's name."
        } else if (TextUtils.isEmpty(updateEntity_contact_primary_et.text.toString())) {
            updateEntity_contact_primary.error = "Please enter primary contact no."
        } else if (updateEntity_contact_primary_et.text.toString().length != 10) {
            updateEntity_contact_primary.error = "Please enter valid primary contact no."
        } else if (!TextUtils.isEmpty(updateEntity_contact_alt_et.text.toString()) && updateEntity_contact_alt_et.text.toString().length != 10) {
            updateEntity_contact_alternate.error = "Please enter valid alternate contact no."
        } else if (TextUtils.isEmpty(updateEntity_amount_et.editableText.toString())) {
            updateEntity_amount.error = "Please enter opening amount balance."
        } else if (rating == 0f) {
            showDialog(
                "Error",
                "Please give rating to the organisation .",
                R.drawable.ic_alert_error_24dp
            )
        } else {
            return true
        }

        return false
    }


    private fun updateEntity(): Entity {

        val entity = Entity()
        entity.id = receivedId
        entity.companyName = updateEntity_firm_name_et.text.toString()
        entity.companyOwner = updateEntity_owner_name_et.text.toString()
        entity.companyAddress = updateEntity_address_et.text.toString()
        entity.primaryContactNo = updateEntity_contact_primary_et.text.toString()
        entity.altContactNo = updateEntity_contact_alt_et.text.toString()
        entity.gstNo = updateEntity_gst_et.text.toString()
        entity.totalAmount = updateEntity_amount_et.text.toString().toFloat()
        entity.entityType = entityType
        entity.txnDateTime = creationDate!!
        entity.entityRating = rating

        return entity
    }

    override fun sendCallback(positiveAction: Boolean) {
        if (positiveAction) {
            finish()
        }
    }
}
