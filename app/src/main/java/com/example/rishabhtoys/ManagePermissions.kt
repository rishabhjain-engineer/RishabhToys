package com.example.rishabhtoys

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class ManagePermissions(val activity: Activity, val list: List<String>, val code: Int) {


    private lateinit var permissionName:String
    private lateinit var permissionDescription:String
    // Check permissions at runtime
    fun checkPermissions() {
        if (isPermissionsGranted() != PackageManager.PERMISSION_GRANTED) {
            showAlert()
        }
    }

    // Check permissions status
    private fun isPermissionsGranted(): Int {
        // PERMISSION_GRANTED : Constant Value: 0
        // PERMISSION_DENIED : Constant Value: -1
        var counter = 0;
        for (permission in list) {
            counter += ContextCompat.checkSelfPermission(activity, permission)
        }
        return counter
    }

    // Find the first denied permission
    private fun deniedPermission(): String {
        for (permission in list) {
            if (ContextCompat.checkSelfPermission(activity, permission)
                == PackageManager.PERMISSION_DENIED
            ) return permission
        }
        return ""
    }

    // Show alert dialog to request permissions
    private fun showAlert() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Need permission(s)")
        builder.setCancelable(false)
        builder.setMessage("Camera and Storage permissions are required for this app. Kindly grant permission.")
        builder.setPositiveButton("OK") { dialog, which -> requestPermissions() }
        builder.setNeutralButton("Cancel") { dialog, which -> dialog.dismiss(); activity.finish() }
        val dialog = builder.create()
        dialog.show()
    }


    // Request the permissions at run time
    private fun requestPermissions() {
        val permission = deniedPermission()


        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {

            when(permission){

                "android.permission.CAMERA" -> {
                    permissionName = "Camera"
                    permissionDescription = " permission is required for this app to capture photos and videos. Kindly grant this permission. Otherwise you won't be able to access the application."
                }
                else ->  {
                    permissionName = "Storage"
                    permissionDescription = " permission is required for this app to perform read and write operation of data files. Kindly grant this permission. Otherwise you won't be able to access the application."

                }

            }

            // Show an explanation asynchronously
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Need permission(s)")
            builder.setCancelable(false)
            builder.setMessage(permissionName.plus(permissionDescription))
            builder.setPositiveButton("OK") { dialog, which -> ActivityCompat.requestPermissions(activity, list.toTypedArray(), code) }
            builder.setNeutralButton("Cancel") { dialog, which -> dialog.dismiss(); activity.finish() }
            val dialog = builder.create()
            dialog.show()
        } else {
            ActivityCompat.requestPermissions(activity, list.toTypedArray(), code)
        }
    }


    // Process permissions result
    fun processPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ): Boolean {
        var result = 0
        if (grantResults.isNotEmpty()) {
            for (item in grantResults) {
                result += item
            }
        }
        if (result == PackageManager.PERMISSION_GRANTED){
            return true
        }else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, deniedPermission())) {
                openSettingsPage()
            }
        }
        return false
    }

    private fun openSettingsPage() {
        val i = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:" + BuildConfig.APPLICATION_ID)
        )
        activity.startActivity(i)
    }

}