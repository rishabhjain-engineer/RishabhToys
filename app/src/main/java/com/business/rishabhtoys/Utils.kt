package com.business.rishabhtoys

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    companion object {

        fun getCurrentDateString() : String{
            val date = Calendar.getInstance().time
            val formatter = SimpleDateFormat("dd.MM.yyyy",Locale.ENGLISH)
            return formatter.format(date)
        }

        fun getDateFromString(dateString : String) : Date {
            val formatter = SimpleDateFormat("dd.MM.yyyy",Locale.ENGLISH)
            return formatter.parse(dateString);
        }

        fun getStringFromDate(date: Date) : String{
            val formatter = SimpleDateFormat("dd.MM.yyyy",Locale.ENGLISH)
            return formatter.format(date)
        }


        fun createBackup(context: Context) {

            // Get access to Db directory location and fetch all files in it
            val data = Environment.getDataDirectory()
            val currentDbPath = "//data//com.business.rishabhtoys//databases//"
            val dbDirectory = File(data, currentDbPath)
            val fileList = dbDirectory.listFiles()

            //create a new directory, in internal storage.
            val outputDirectory = File(context.filesDir, "Backup")
            if (!outputDirectory.exists()) {
                outputDirectory.mkdir()
            }

            // Copy all files from Db directory to newly created directory
            if (fileList.isNotEmpty()) {
                fileList?.forEach {
                    val outputFilename = it.name
                    val backupDir = File(outputDirectory, outputFilename)
                    val src = FileInputStream(it).channel
                    val destination = FileOutputStream(backupDir).channel
                    destination.transferFrom(src, 0, src.size())
                    src.close()
                    destination.close()
                }

            }
        }

        fun displayFormattedAmount(amount: Float?): String {
            val df = DecimalFormat("##,##,###.##")
            return df.format(amount)
        }

        private fun createPdf(context: Context) {


            val pdfDocument = PdfDocument()
            val paint = Paint()

            val pageInfo = PdfDocument.PageInfo.Builder(400,600,1).create()
            val firstPage = pdfDocument.startPage(pageInfo)

            val canvas = firstPage.canvas

            paint.textAlign = Paint.Align.CENTER
            paint.color = Color.RED
            paint.textSize = 16f
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD);
            canvas.drawText( "RISHABH TOYS" , (pageInfo.pageWidth/2).toFloat(), 30f,paint)

            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            paint.color = Color.BLACK
            paint.textSize = 10f
            canvas.drawText( "Shop No. 2895/3, IIIrd floor, Janta Market, Sadar Bazar, Delhi-110006" , (pageInfo.pageWidth/2).toFloat(), 45f,paint)
            canvas.drawText( "GST no: 07ACHPJ9322R1ZZ" , (pageInfo.pageWidth/2).toFloat(), 60f,paint)
            canvas.drawText( "Mob no: +91-9868676991 | +91-7827820919" , (pageInfo.pageWidth/2).toFloat(), 75f,paint)
            canvas.drawText( "Email: djain986867@gmail.com" , (pageInfo.pageWidth/2).toFloat(), 90f,paint)
            canvas.drawLine(10f , 105f , (pageInfo.pageWidth-10).toFloat(), 105f , paint)


            paint.style = Paint.Style.STROKE
            paint.color = Color.BLACK
            paint.strokeWidth = 3f
            canvas.drawRect(20f, 120f , (pageInfo.pageWidth-20).toFloat(), 225f,paint)

            paint.style = Paint.Style.FILL
            paint.textAlign = Paint.Align.LEFT
            paint.color = Color.BLACK
            paint.textSize = 14f
            canvas.drawText("Company",30f, 140f, paint)
            canvas.drawText("Address",30f, 165f, paint)
            canvas.drawText("GST No.",30f, 190f, paint)
            canvas.drawText("Contact No.",30f, 215f, paint)

            canvas.drawLine(110f , 120f , 110f , 225f , paint)

            canvas.drawText("Hari Om Traders",120f, 140f, paint)
            canvas.drawText("kalupur, Ahemdabad, Gujrat",120f, 165f, paint)
            canvas.drawText("07UC3PJ4322R1VZ",120f, 190f, paint)
            canvas.drawText("9654239943",120f, 215f, paint)

            paint.color = Color.parseColor("#eba832")
            canvas.drawRect(0f, 240f , (pageInfo.pageWidth).toFloat(), 280f,paint)

            paint.color = Color.WHITE
            paint.textAlign = Paint.Align.CENTER
            paint.textSize = 16f
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD);
            canvas.drawText("Transaction History",(pageInfo.pageWidth/2).toFloat(), 265f, paint)

            paint.color = Color.BLACK
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 1f
            canvas.drawLine(0f,281f, pageInfo.pageWidth.toFloat(), 281f,paint)
            canvas.drawLine(0f,311f, pageInfo.pageWidth.toFloat(), 311f,paint)

            //Txn History Vertical Line
            canvas.drawLine(130f,281f, 130f, pageInfo.pageHeight.toFloat(),paint)
            canvas.drawLine(265f,281f, 265f, pageInfo.pageHeight.toFloat(),paint)


            paint.style = Paint.Style.FILL
            paint.textSize = 14f
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            paint.textAlign = Paint.Align.CENTER


            canvas.drawText("Txn Date" , 62f, 301f,  paint)
            canvas.drawText("Txn Amount" , 194f, 301f,  paint)
            canvas.drawText("Balance" , 326f, 301f,  paint)


            pdfDocument.finishPage(firstPage)

            val file = File(context.getExternalFilesDir(null), "/RishabhToys.pdf" )

            pdfDocument.writeTo(FileOutputStream(file))
            pdfDocument.close()

        }

        fun removeSpaceFromString(data:String): String{
            var modifiedData = data
            if(data.contains(" ")){
                modifiedData = data.replace(" ","")
            }
            return modifiedData
        }

        fun getPdfAddressFields(address:String) : Array<String>{

            var firstField:String
            val secondField :String

            // character at 36th position is space, simply split and return
            if(" " == address[36].toString()){
                firstField = address.substring(0,36)
                secondField = address.substring(36,address.length)
            }else {
                firstField = address.substring(0,36)
                val lastSpaceIndex = firstField.lastIndexOf(" ")
                firstField = firstField.substring(0,lastSpaceIndex)
                secondField = address.substring(lastSpaceIndex,address.length)
            }

            return arrayOf(firstField,secondField)
        }

    }
}