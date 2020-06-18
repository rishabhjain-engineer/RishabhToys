package com.example.rishabhtoys

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TxnHistoryAdapter : RecyclerView.Adapter<TxnHistoryAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.txn_history_single, parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 20;
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}