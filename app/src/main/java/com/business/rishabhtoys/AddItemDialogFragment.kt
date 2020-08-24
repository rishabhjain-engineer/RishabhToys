package com.business.rishabhtoys

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_add_item_dialog.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 */
class AddItemDialogFragment : DialogFragment() {

    private var receivedId : Long? = null

    companion object {

        fun getAddDialogFragment(purchaseId : Long) : AddItemDialogFragment {
            val argument = Bundle()
            argument.putLong("purchaseId",purchaseId)
            val addItemDialogFragment = AddItemDialogFragment()
            addItemDialogFragment.arguments = argument
            return addItemDialogFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_item_dialog, container, false)
    }



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        receivedId = arguments?.getLong("purchaseId")

        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.getWindow()?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.window?.setBackgroundDrawable(null)
            dialog.setCancelable(true);
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        add_item_btn.setOnClickListener {

            if(TextUtils.isEmpty(addItem_item_name_et.text.toString())){
                addItem_item_name_et.error = "Kindly enter item name"
            }else if(TextUtils.isEmpty(addyItem_price_et.text.toString())){
                addyItem_price_et.error = "Kindly enter price of the item."
            }else {
                val result = GlobalScope.async {
                    val rateListEntity = RateListEntity()
                    rateListEntity.purchaseId = receivedId
                    rateListEntity.itemName = addItem_item_name_et.editableText.toString()
                    rateListEntity.itemPrice = addyItem_price_et.editableText.toString().toFloat()
                    val repository = Repository(activity?.application!!)
                    repository.addItemToRateList(rateListEntity)
                }

                GlobalScope.launch (Dispatchers.Main){
                    val status: Long? = result.await()
                    val ret: Int? = status?.compareTo(0)
                    if (ret != null && ret >= 0) {
                        (activity as BaseActivity).showDialog(
                            "Success",
                            "Item inserted successfully !!",
                            R.drawable.ic_check_circle_24dp
                        )
                        dialog?.dismiss()
                    } else {
                        (activity as BaseActivity).showDialog(
                            "Error",
                            "Item couldn't be inserted !!",
                            R.drawable.ic_alert_error_24dp
                        )
                    }
                }
            }

        }
    }



}
