package com.business.rishabhtoys

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView


class AutoCompleteCustomAdapter(context: Context, list: List<EntityTransData>?) :
    ArrayAdapter<EntityTransData>(context, 0, list!!) {


    private var entityTransDataList: MutableList<EntityTransData> = ArrayList(list!!)


    override fun getFilter(): Filter {
        return entityFilter
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val retView: View
        val holder: ViewHolder

        if (convertView == null) {
            retView = LayoutInflater.from(parent.context)
                .inflate(R.layout.select_dialog_item, parent, false)
            holder = ViewHolder()
            holder.firmNameTv = retView.findViewById(R.id.firm_name_tv)
            retView?.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
            retView = convertView
        }
        val entity: EntityTransData? = getItem(position)
        holder.firmNameTv?.text = entity?.companyName

        return retView
    }

    private var entityFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {

            val results = FilterResults()

            val suggestions: MutableList<EntityTransData> = ArrayList()

            if (constraint == null || constraint.isEmpty()) {
                suggestions.addAll(entityTransDataList)
            } else {
                val stringPattern = constraint.toString().toLowerCase().trim()
                for (entity in entityTransDataList) {
                    if (entity.companyName.toLowerCase().startsWith(stringPattern)) {
                        suggestions.add(entity)
                    }
                }
            }
            results.values = suggestions
            results.count = suggestions.size

            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

            val filterList: List<EntityTransData> = results!!.values as ArrayList<EntityTransData>
            if (results.count > 0) {
                clear()
                for (entity in filterList) {
                    add(entity)
                    notifyDataSetChanged()
                }
            }
        }

        override fun convertResultToString(resultValue: Any?): CharSequence {
            return (resultValue as EntityTransData).companyName
        }

    }

    internal class ViewHolder {
        var firmNameTv: TextView? = null
    }

}



