package com.business.rishabhtoys

import android.content.Context
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

    }
}