package com.example.rishabhtoys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_report.*
import kotlinx.android.synthetic.main.select_dialog_item.*


/**
 * A simple [Fragment] subclass.
 */
class ReportFragment : Fragment() {

    var fruits = arrayOf(
        "Apple",
        "Aam",
        "Papaya",
        "Banana",
        "Cherry",
        "Date",
        "Grape",
        "Kiwi",
        "Kaju",
        "Mango",
        "Water melon",
        "Musk melon",
        "Guava",
        "Pear"
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(activity!!, R.layout.select_dialog_item,R.id.firm_name_tv, fruits)
        autoCompleteTextView.threshold = 1 //will start working from first character
        autoCompleteTextView.setAdapter(adapter) //setting the adapter data into the AutoCompleteTextView

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
