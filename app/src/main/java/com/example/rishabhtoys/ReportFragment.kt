package com.example.rishabhtoys

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_report.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 */
class ReportFragment : Fragment() {

    private var mListOfCompanies: List<EntityTransData>? = ArrayList()
    private lateinit var autoCompleteCustomAdapter: AutoCompleteCustomAdapter
    private var selectedEntityTransData: EntityTransData? = null
    private lateinit var mRepository: Repository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mRepository = Repository(activity!!.application)
        group.visibility = GONE
        val result = GlobalScope.async {
            mRepository.getCompanyNameList()
        }

        GlobalScope.launch(Dispatchers.Main) {
            mListOfCompanies = result.await()
            autoCompleteCustomAdapter =
                AutoCompleteCustomAdapter(activity!!, mListOfCompanies)
            autoCompleteTextView.setAdapter(autoCompleteCustomAdapter) //setting the adapter data into the AutoCompleteTextView
        }

        report_date.text = Utils.getTxnDateTime()
        group.visibility = VISIBLE
        calender.visibility = GONE

        report_date.setOnClickListener {
            group.visibility = GONE
            calender.visibility = View.VISIBLE
        }

        calender.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calender.visibility = GONE
            group.visibility = View.VISIBLE
            report_date.text = dayOfMonth.toString().plus(".").plus(month + 1).plus(".").plus(year)
        }



        autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            selectedEntityTransData = parent?.getItemAtPosition(position) as EntityTransData
            report_balance.text = resources.getString(R.string.inr).plus(" ")
                .plus(selectedEntityTransData?.totalAmount.toString())
            autoCompleteTextView.isCursorVisible = false
        }

        autoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                selectedEntityTransData = null
                autoCompleteTextView.isCursorVisible = true
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                selectedEntityTransData = null
                autoCompleteTextView.isCursorVisible = true
            }

        })


        report_log_btn.setOnClickListener {
            if (validationCheck()) {
                submitLog()
            }
        }


    }

    private fun submitLog() {

        val txnHistoryEntity = TxnHistoryEntity()
        txnHistoryEntity.entityId = selectedEntityTransData?.id
        txnHistoryEntity.date = report_date.text.toString()
        txnHistoryEntity.txnAmount = report_amount.text.toString().toFloat()

        if (goods_payment_button_view.checkedRadioButtonId.equals(R.id.goods_radio)) {
            txnHistoryEntity.txnType = TxnType.GOODS
        } else if (goods_payment_button_view.checkedRadioButtonId.equals(R.id.payment_radio)) {
            txnHistoryEntity.txnType = TxnType.PAYMENT
            txnHistoryEntity.txnColorCode = txnPaymentColor
        }
        if (!TextUtils.isEmpty(report_description.text.toString())) {
            txnHistoryEntity.remark = report_description.text.toString()
        }

        val insertAndUpdateResult = GlobalScope.async {
            mRepository.insertAndUpdate(
                txnHistoryEntity,
                selectedEntityTransData?.id!!,
                calculateUpdatedAmount()!!
            )
        }

        GlobalScope.launch(Dispatchers.Main) {
            val status: Long? = insertAndUpdateResult.await()
            val ret: Int? = status?.compareTo(0)
            if (ret != null && ret >= 0) {
                clearUI()
                (activity as BaseActivity).showDialog(
                    "Success",
                    "Transaction saved successfully !!",
                    R.drawable.ic_check_circle_24dp
                )
            } else {
                (activity as BaseActivity).showDialog(
                    "Error",
                    "Transaction couldn't be saved !!",
                    R.drawable.ic_alert_error_24dp
                )
            }
        }

    }

    private fun clearUI() {
        autoCompleteTextView.text.clear()
        report_balance.text = ""
        report_amount.setText("")
        report_description.setText("")
    }


    private fun validationCheck(): Boolean {
        if (selectedEntityTransData == null) {
            (activity as BaseActivity).showDialog(
                "Error",
                "Kindly select organisation name !!",
                R.drawable.ic_alert_error_24dp
            )
        } else if (TextUtils.isEmpty(report_amount.text.toString())) {
            (activity as BaseActivity).showDialog(
                "Error",
                "Kindly enter transaction amount !!",
                R.drawable.ic_alert_error_24dp
            )
        } else {
            return true
        }
        return false
    }


    /*
    *
    * Purchase Mode :
    * if goods option selected : add amount to the balance, since we need to pay to factory
    * if payment option selected: deduct amount from balance: since we had pay x amount.
    *
    *
    * Sale Mode:
    * if good option selected: add amount to balance, since total amount we have to take from our party
    * if payment option selected: deduct amount from balance, since party paid us x amount.
    *
    * */

    private fun calculateUpdatedAmount(): Float? {
        if (goods_payment_button_view.checkedRadioButtonId.equals(R.id.goods_radio)) {
            selectedEntityTransData?.totalAmount =
                selectedEntityTransData?.totalAmount?.plus(report_amount.text.toString().toFloat())!!
        } else if (goods_payment_button_view.checkedRadioButtonId.equals(R.id.payment_radio)) {
            selectedEntityTransData?.totalAmount =
                selectedEntityTransData?.totalAmount?.minus(report_amount.text.toString().toFloat())!!
        }
        return selectedEntityTransData?.totalAmount

    }
}


