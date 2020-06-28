package com.example.rishabhtoys

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TxnHistoryAdapter : RecyclerView.Adapter<TxnHistoryAdapter.MyViewHolder>() {

    private var list : List<TxnHistoryEntity> = ArrayList()

    fun setData(list : List<TxnHistoryEntity>){
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.txn_history_single, parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size;
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.dateTv.text = list.get(position).date
        holder.amountTv.text = list.get(position).txnAmount.toString()
        holder.balanceTv.text = list.get(position).txnAmount.toString()
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val dateTv:TextView = itemView.findViewById(R.id.txn_date)
        val amountTv:TextView = itemView.findViewById(R.id.txn_amount)
        val balanceTv:TextView = itemView.findViewById(R.id.txn_balance)
    }
}