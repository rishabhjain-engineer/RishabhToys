package com.business.rishabhtoys

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.FileContent
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.drive.model.File
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        create_backup_btn.setOnClickListener {
            googleSignIn()
            //Utils.createBackup(activity!!)
        }
    }

    private fun googleSignIn(){
        val signIn = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestScopes(Scope(DriveScopes.DRIVE_FILE))
            .build()

        val client : GoogleSignInClient = GoogleSignIn.getClient(activity!!,signIn)
        startActivityForResult(client.signInIntent , 400)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            400 -> {
                if(resultCode == Activity.RESULT_OK){
                    Log.e("Rishabh", "handle sign in")
                    handleSignIn(data)
                }
            }
        }
    }


    private fun handleSignIn(data: Intent?) {

        GoogleSignIn.getSignedInAccountFromIntent(data)
            .addOnSuccessListener {

                Log.e("Rishabh", "sign in success")
                val credentials : GoogleAccountCredential = GoogleAccountCredential
                    .usingOAuth2(activity!!, Collections.singleton(DriveScopes.DRIVE_FILE))

                credentials.selectedAccount = it.account

                val driveService : Drive = Drive.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    GsonFactory(),
                    credentials)
                    .setApplicationName("Rishabh Toy's Drive")
                    .build()


              /*  // Upload your files to drive


                val fileMetadata = File()
                fileMetadata.name = "Invoices1234"
                fileMetadata.mimeType = "application/vnd.google-apps.folder"
                // application/vnd.sqlite3, application/x-sqlite3


                GlobalScope.launch (Dispatchers.IO){
                    val file: File =
                        driveService.files().create(fileMetadata)
                            .setFields("id")
                            .execute()
                }

*/

                // Get access to Db directory location and fetch all files in it
                val data = Environment.getDataDirectory()
                val currentDbPath = "//data//com.business.rishabhtoys//databases//"
                val dbDirectory = java.io.File(data, currentDbPath)
                val fileList = dbDirectory.listFiles()

                Log.e("Rishabh","file list size: "+fileList.size)
                GlobalScope.launch (Dispatchers.IO){
                    // Upload each file
                    if (fileList.isNotEmpty()) {
                        fileList?.forEach {

                            Log.e("Rishabh","file name: "+it.name)

                            val fileMetadata = File()
                            fileMetadata.name = it.name

                            val filePath: java.io.File = java.io.File(it.path)
                            val fileContent : FileContent = FileContent("application/vnd.sqlite3",filePath)

                            val file: File =
                                driveService.files().create(fileMetadata , fileContent)
                                    .setFields("id")
                                    .execute()

                            withContext(Dispatchers.Main){
                                System.out.println("File ID: " + file.getId());
                                Log.e("Rishabh","file id: "+file.id)
                            }
                        }

                    }
                }


            }
    }

}
