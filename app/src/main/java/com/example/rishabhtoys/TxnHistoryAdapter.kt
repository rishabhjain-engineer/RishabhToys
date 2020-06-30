package com.example.rishabhtoys

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TxnHistoryAdapter : RecyclerView.Adapter<TxnHistoryAdapter.MyViewHolder>() {

    private var list : List<TxnHistoryEntity> = ArrayList()
    private var balanceAmount:Float? = 0f

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


        if(position == 0){
            balanceAmount = list[0].txnAmount
            holder.amountTv.text = list.get(position).txnAmount.toString()
        }else{
            if(0 == list[position].txnType) {
                // txn type = debit; money will go out of our pocket
                balanceAmount = balanceAmount?.minus(list[position].txnAmount!!)
                holder.amountTv.text = "- ".plus(list.get(position).txnAmount.toString())
            }else{
                // txn type = credit; money will come to our pocket
                balanceAmount = balanceAmount?.plus(list[position].txnAmount!!)
                holder.amountTv.text = "+ ".plus(list.get(position).txnAmount.toString())
            }
        }
        holder.balanceTv.text = balanceAmount.toString()
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val dateTv:TextView = itemView.findViewById(R.id.txn_date)
        val amountTv:TextView = itemView.findViewById(R.id.txn_amount)
        val balanceTv:TextView = itemView.findViewById(R.id.txn_balance)
    }
}