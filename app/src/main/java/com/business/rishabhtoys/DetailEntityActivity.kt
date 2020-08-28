package com.business.rishabhtoys

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_detail_entity.*
import java.io.File
import java.io.FileOutputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class DetailEntityActivity : BaseActivity() {

    private var receivedEntityId: Long? = 0
    private var entity: Entity? = null
    private lateinit var mViewModel: DetailEntityViewModel
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: TxnHistoryAdapter
    private var mTxnHistoryList : MutableList<TxnHistoryEntity> = ArrayList()
    private var queryDataList:ArrayList<DetailInfoForEntity> = ArrayList<DetailInfoForEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_entity)

        if (intent != null) {
            receivedEntityId = intent.getLongExtra("entityId", 0)
        }

        mLayoutManager = LinearLayoutManager(this)
        txn_history_rv.layoutManager = mLayoutManager
        mAdapter = TxnHistoryAdapter()
        txn_history_rv.adapter = mAdapter

        mViewModel = ViewModelProvider(this).get(DetailEntityViewModel::class.java)
        if (receivedEntityId != null) {
            mViewModel.getDetailEntityInfo(receivedEntityId)
        }

        d_e_rate_list_iv.setOnClickListener {
            val intent = Intent(this , RateListActivity::class.java)
            intent.putExtra("purchaseId",receivedEntityId)
            startActivity(intent)
        }

        d_e_edit.setOnClickListener {
            val intent = Intent(this , UpdateEntityActivity::class.java)
            intent.putExtra("entityId",receivedEntityId)
            startActivity(intent)
        }

        save_pdf_btn.setOnClickListener {
            savePdf()
        }

        mViewModel.getData()?.observe(this, Observer<List<DetailInfoForEntity>> {

            if (it.isNotEmpty()) {

                // saving data
                queryDataList = it as ArrayList<DetailInfoForEntity>

                d_e_company_name.text = it[0].entity.companyName
                d_e_company_address.text = it[0].entity.companyAddress
                d_e_gst.text = it[0].entity.gstNo
                val entityType = it[0].entity.entityType
                if (0 == entityType) {
                    d_e_entity_type.text = Purchase
                    d_e_rate_list_iv.visibility = View.VISIBLE
                }else if(1 == entityType){
                    d_e_entity_type.text = Sale
                    d_e_rate_list_iv.visibility = View.GONE
                }else{
                    d_e_entity_type.text = "UnKnown"
                }
                d_e_company_owner.text = it[0].entity.companyOwner
                d_e_primary_no.text = it[0].entity.primaryContactNo
                d_e_alt_no.text = it[0].entity.altContactNo
                d_e_rating.rating = it[0].entity.entityRating!!
            }

            mTxnHistoryList.clear()
            it.forEach {
                mTxnHistoryList.add(it.txnHistoryEntity)
            }

            if(mTxnHistoryList.isNotEmpty()) {
                Collections.sort(mTxnHistoryList , object : Comparator<TxnHistoryEntity> {
                    var dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy",Locale.ENGLISH)
                    override fun compare(o1: TxnHistoryEntity?, o2: TxnHistoryEntity?): Int =
                        dateFormat.parse(Utils.getStringFromDate(o1?.date!!)).compareTo(dateFormat.parse(Utils.getStringFromDate(o2?.date!!)))

                })

                mAdapter.setData(mTxnHistoryList)
                mAdapter.notifyDataSetChanged()
            }

        })
    }

    private fun savePdf() {

        var yCoordinate:Float = 0f
        val pdfDocument = PdfDocument()
        val paint = Paint()

        val pageInfo = PdfDocument.PageInfo.Builder(400,600,1).create()
        val firstPage = pdfDocument.startPage(pageInfo)

        val canvas = firstPage.canvas

        paint.textAlign = Paint.Align.CENTER
        paint.color = Color.RED
        paint.textSize = 16f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD);
        yCoordinate = 30f
        canvas.drawText( getString(R.string.rishabh_toys) , (pageInfo.pageWidth/2).toFloat(), yCoordinate,paint)

        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        paint.color = Color.BLACK
        paint.textSize = 10f
        canvas.drawText( getString(R.string.rishabh_toys_address) , (pageInfo.pageWidth/2).toFloat(), 45f,paint)
        canvas.drawText( getString(R.string.rishabh_toys_gst) , (pageInfo.pageWidth/2).toFloat(), 60f,paint)
        canvas.drawText( getString(R.string.rishabh_toys_contact) , (pageInfo.pageWidth/2).toFloat(), 75f,paint)
        canvas.drawText( getString(R.string.rishabh_toys_email) , (pageInfo.pageWidth/2).toFloat(), 90f,paint)
        canvas.drawLine(10f , 105f , (pageInfo.pageWidth-10).toFloat(), 105f , paint)
        yCoordinate = 105f


        paint.style = Paint.Style.FILL
        paint.textAlign = Paint.Align.LEFT
        paint.color = Color.BLACK
        paint.textSize = 14f
        yCoordinate = 140f

        canvas.drawText(getString(R.string.company_pdf_label),30f, yCoordinate, paint)
        canvas.drawText(queryDataList[0].entity.companyName,120f, yCoordinate, paint)

        canvas.drawText(getString(R.string.address_pdf_label),30f, yCoordinate+25, paint)
        val addressArray = Utils.getPdfAddressFields(queryDataList[0].entity.companyAddress)
        yCoordinate += 25
        if(addressArray.isNotEmpty()) {
            canvas.drawText(addressArray[0],120f, yCoordinate, paint)
            if(!TextUtils.isEmpty(addressArray[1])){
                yCoordinate += 15
                canvas.drawText(addressArray[1],120f, yCoordinate, paint)
            }
        }

        yCoordinate += 25
        canvas.drawText(getString(R.string.gst_pdf_label),30f, yCoordinate, paint)
        canvas.drawText(queryDataList[0].entity.gstNo,120f, yCoordinate, paint)

        yCoordinate += 25
        canvas.drawText(getString(R.string.contact_pdf_label),30f, yCoordinate, paint)
        canvas.drawText(queryDataList[0].entity.primaryContactNo,120f, yCoordinate, paint)

        yCoordinate += 15
        paint.style = Paint.Style.STROKE
        paint.color = Color.BLACK
        paint.strokeWidth = 3f
        canvas.drawRect(20f, 120f , (pageInfo.pageWidth-20).toFloat(), yCoordinate,paint)
        canvas.drawLine(110f , 120f , 110f , yCoordinate , paint)


        yCoordinate += 15
        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor("#eba832")
        canvas.drawRect(0f, yCoordinate , (pageInfo.pageWidth).toFloat(), yCoordinate+50,paint)

        paint.color = Color.WHITE
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = 16f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText(getString(R.string.transaction_history),(pageInfo.pageWidth/2).toFloat(), yCoordinate+30, paint)

        yCoordinate += 50


        //Horizontal lines beneath Txn History Labels
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1f
        canvas.drawLine(0f,yCoordinate, pageInfo.pageWidth.toFloat(), yCoordinate,paint)
        yCoordinate += 30
        canvas.drawLine(0f,yCoordinate, pageInfo.pageWidth.toFloat(), yCoordinate,paint)

        //Txn History Vertical Line
        canvas.drawLine(130f,yCoordinate-30, 130f, pageInfo.pageHeight.toFloat(),paint)
        canvas.drawLine(265f,yCoordinate-30, 265f, pageInfo.pageHeight.toFloat(),paint)


        paint.style = Paint.Style.FILL
        paint.textSize = 14f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        paint.textAlign = Paint.Align.CENTER


        canvas.drawText(getString(R.string.date_pdf_label) , 62f, yCoordinate-12,  paint)
        canvas.drawText(getString(R.string.amount_pdf_label), 194f, yCoordinate-12,  paint)
        canvas.drawText(getString(R.string.balance_pdf_label) , 326f, yCoordinate-12,  paint)

        yCoordinate += 30
        mTxnHistoryList.forEach {

            if(yCoordinate < 600) {
                canvas.drawText(Utils.getStringFromDate(it.date) , 62f, yCoordinate,  paint)
                canvas.drawText(it.txnAmount.toString() , 194f, yCoordinate,  paint)
                canvas.drawText(it.txnAmount.toString() , 326f, yCoordinate,  paint)

                yCoordinate += 25f
            }

        }


        pdfDocument.finishPage(firstPage)
        val file = File(getExternalFilesDir(null), Utils.removeSpaceFromString(queryDataList[0].entity.companyName).plus("_").plus(Utils.getCurrentDateString()).plus(".pdf"))

        Log.e("Rishabh","file path: "+file.path)

        pdfDocument.writeTo(FileOutputStream(file))
        pdfDocument.close()
    }
}
