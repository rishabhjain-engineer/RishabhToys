package com.example.rishabhtoys

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_report.*
import kotlinx.android.synthetic.main.select_dialog_item.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 */
class ReportFragment : Fragment() {

    private var mListOfCompanies : List<EntityTransData>? = ArrayList()
    private lateinit var autoCompleteCustomAdapter: AutoCompleteCustomAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val repository = Repository(activity!!.application)


        val result = GlobalScope.async {
            repository.getCompanyNameList()
        }

        GlobalScope.launch (Dispatchers.Main) {
            mListOfCompanies = result.await()
            autoCompleteCustomAdapter = AutoCompleteCustomAdapter(activity!! ,mListOfCompanies )
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
            report_date.text = dayOfMonth.toString().plus(".").plus(month+1).plus(".").plus(year)
        }




    }
}
