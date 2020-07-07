package com.example.rishabhtoys

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RateListAdapter : RecyclerView.Adapter<RateListAdapter.MyViewHolder>() {


    private var list: List<RateListEntity> = ArrayList()

    fun setDataSet(list: List<RateListEntity>) {
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_row_rate_list, parent, false)
        val holder = MyViewHolder(view)
        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemNameTv.text = list[position].itemName
        holder.itemPriceTv.text = list[position].itemPrice.toString()
        holder.itemSrTv.text = position.toString()

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNameTv: TextView = itemView.findViewById(R.id.item_name_tv)
        val itemPriceTv: TextView = itemView.findViewById(R.id.item_price_tv)
        val itemSrTv: TextView = itemView.findViewById(R.id.item_sr_tv)
    }
}