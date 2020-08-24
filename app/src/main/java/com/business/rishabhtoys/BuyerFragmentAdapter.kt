package com.business.rishabhtoys

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BuyerFragmentAdapter(private val listener:SendEntityObject) :
    RecyclerView.Adapter<BuyerFragmentAdapter.MyViewHolder>() {

    var list:List<EntityTransData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.single_entity, parent, false)
        return MyViewHolder(view)
    }

    fun setData(list:List<EntityTransData>){
        this.list = list
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.serialNo.text = (position + 1).toString()
        holder.companyName.text = list.get(position).companyName
        holder.amount.text = list.get(position).totalAmount.toString()

        holder.itemView.setOnClickListener {
            listener.sendEntity(list.get(position))
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val companyName: TextView = itemView.findViewById(R.id.purchase_company_name)
        val amount: TextView = itemView.findViewById(R.id.purchase_amount)
        val serialNo: TextView = itemView.findViewById(R.id.purchase_srno)
    }
}