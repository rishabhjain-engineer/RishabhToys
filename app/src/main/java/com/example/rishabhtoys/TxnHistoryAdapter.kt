package com.example.rishabhtoys

import android.graphics.Color
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
            holder.amountTv.text = Utils.displayFormattedAmount(list.get(position).txnAmount)
        }else{
            if(TxnType.GOODS.equals(list[position].txnType)) {
                balanceAmount = balanceAmount?.plus(list[position].txnAmount!!)
                holder.amountTv.text = "+ ".plus(Utils.displayFormattedAmount(list.get(position).txnAmount))
            }else if(TxnType.PAYMENT.equals(list[position].txnType)){
                balanceAmount = balanceAmount?.minus(list[position].txnAmount!!)
                holder.amountTv.text = "- ".plus(Utils.displayFormattedAmount(list.get(position).txnAmount))
                holder.itemView.setBackgroundColor(list[position].txnColorCode)
            }
        }

        holder.balanceTv.text = Utils.displayFormattedAmount(balanceAmount)


    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val dateTv:TextView = itemView.findViewById(R.id.txn_date)
        val amountTv:TextView = itemView.findViewById(R.id.txn_amount)
        val balanceTv:TextView = itemView.findViewById(R.id.txn_balance)
    }
}