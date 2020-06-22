package com.example.rishabhtoys

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
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
class ReportFragment : Fragment(), AutoCompleteCustomAdapter.SendEntityObject {

    private var mListOfCompanies: List<EntityTransData>? = ArrayList()
    private lateinit var autoCompleteCustomAdapter: AutoCompleteCustomAdapter
    private lateinit var selectedEntityTransData: EntityTransData
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


        val result = GlobalScope.async {
            mRepository.getCompanyNameList()
        }

        GlobalScope.launch(Dispatchers.Main) {
            mListOfCompanies = result.await()
            autoCompleteCustomAdapter =
                AutoCompleteCustomAdapter(activity!!, mListOfCompanies, this@ReportFragment)
            autoCompleteTextView.setAdapter(autoCompleteCustomAdapter) //setting the adapter data into the AutoCompleteTextView

        }


        report_date.text = Utils.getTxnDateTime()
        calender.visibility = View.GONE

        report_date.setOnClickListener {

            report_date_label.visibility = View.GONE
            report_date.visibility = View.GONE
            calender.visibility = View.VISIBLE
        }

        calender.setOnDateChangeListener { view, year, month, dayOfMonth ->
            report_date_label.visibility = View.VISIBLE
            report_date.visibility = View.VISIBLE
            calender.visibility = View.GONE
            report_date.text = dayOfMonth.toString().plus(".").plus(month + 1).plus(".").plus(year)
        }


        report_log_btn.setOnClickListener {
            submitLog()
        }


    }

    private fun submitLog() {

        val txnHistoryEntity = TxnHistoryEntity()
        txnHistoryEntity.entityId = selectedEntityTransData.Id
        txnHistoryEntity.date = report_date.toString()
        txnHistoryEntity.txnAmount = report_amount.text.toString().toFloat()

        if (credit_debit_button_view.checkedRadioButtonId.equals(R.id.credit_radio)) {
            txnHistoryEntity.txnType = 1
        } else if (credit_debit_button_view.checkedRadioButtonId.equals(R.id.debit_radio)) {
            txnHistoryEntity.txnType = 0
        }
        if (!TextUtils.isEmpty(report_description.text.toString())) {
            txnHistoryEntity.remark = report_description.text.toString()
        }

        GlobalScope.launch {
            mRepository.insertTxnLog(txnHistoryEntity)
        }

        Log.e("Rishabh","Insertion done")

    }


    override fun sendEntity(entityTransData: EntityTransData) {
        selectedEntityTransData = entityTransData
        report_balance.text = "INR : ".plus(selectedEntityTransData.Txn_Amount.toString())
    }
}
