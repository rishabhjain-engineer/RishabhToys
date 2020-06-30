package com.example.rishabhtoys

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : BaseActivity() {

    private var digitsPressedCounter: Int = 0
    private var isPasswordSet: Boolean = false
    private var inputPasswordValue : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (TextUtils.isEmpty(sharePref?.getLockPattern())) {
            // Create password.
            label.text = "Create your password"
            authenticate_btn.text = "REGISTER"
            isPasswordSet = false
        } else {
            // Authenticate password
            label.text = "Enter your password"
            authenticate_btn.text = "AUTHENTICATE"
            isPasswordSet = true
        }

        one.setOnClickListener {

            if (digitsPressedCounter < 6) {
                password_tv.append(" * ")
                digitsPressedCounter++
                inputPasswordValue = inputPasswordValue.plus("1")
            }

        }

        two.setOnClickListener {
            if (digitsPressedCounter < 6) {
                password_tv.append(" * ")
                digitsPressedCounter++
                inputPasswordValue = inputPasswordValue.plus("2")
            }
        }

        three.setOnClickListener {
            if (digitsPressedCounter < 6) {
                password_tv.append(" * ")
                digitsPressedCounter++
                inputPasswordValue = inputPasswordValue.plus("3")
            }
        }

        four.setOnClickListener {
            if (digitsPressedCounter < 6) {
                password_tv.append(" * ")
                digitsPressedCounter++
                inputPasswordValue = inputPasswordValue.plus("4")
            }
        }

        five.setOnClickListener {
            if (digitsPressedCounter < 6) {
                password_tv.append(" * ")
                digitsPressedCounter++
                inputPasswordValue = inputPasswordValue.plus("5")
            }
        }

        six.setOnClickListener {
            if (digitsPressedCounter < 6) {
                password_tv.append(" * ")
                digitsPressedCounter++
                inputPasswordValue = inputPasswordValue.plus("6")
            }
        }

        seven.setOnClickListener {
            if (digitsPressedCounter < 6) {
                password_tv.append(" * ")
                digitsPressedCounter++
                inputPasswordValue = inputPasswordValue.plus("7")
            }
        }

        eight.setOnClickListener {
            if (digitsPressedCounter < 6) {
                password_tv.append(" * ")
                digitsPressedCounter++
                inputPasswordValue = inputPasswordValue.plus("8")
            }
        }

        nine.setOnClickListener {
            if (digitsPressedCounter < 6) {
                password_tv.append(" * ")
                digitsPressedCounter++
                inputPasswordValue = inputPasswordValue.plus("9")
            }
        }

        zero.setOnClickListener {
            if (digitsPressedCounter < 6) {
                password_tv.append(" * ")
                digitsPressedCounter++
                inputPasswordValue = inputPasswordValue.plus("0")
            }
        }

        delete.setOnClickListener {
            password_tv.text = ""
            digitsPressedCounter = 0
            inputPasswordValue = ""
        }

        authenticate_btn.setOnClickListener {

            if (isPasswordSet) {
                if (TextUtils.isEmpty(password_tv.text.toString())) {
                    showDialog("Error", "Please enter password !!", R.drawable.ic_alert_error_24dp)
                } else if (inputPasswordValue.length != 6) {
                    showDialog(
                        "Error",
                        "Please enter valid password !!",
                        R.drawable.ic_alert_error_24dp
                    )
                } else {
                    val authenticEncodedKey = sharePref?.getLockPattern()
                    val inputEncodedKey = CryptoHash.getEncodedString(inputPasswordValue)

                    Log.e("Rishabh","retrieve correct encoded key: "+authenticEncodedKey);
                    Log.e("Rishabh","received encoded key: "+inputEncodedKey);

                    if (authenticEncodedKey.equals(inputEncodedKey)) {
                        startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                        finish()
                    } else {
                        showDialog("Error", "Invalid password !!", R.drawable.ic_alert_error_24dp)
                    }
                }
            } else {


                if (TextUtils.isEmpty(password_tv.text.toString())) {
                    showDialog("Error", "Please enter password !!", R.drawable.ic_alert_error_24dp)
                } else if (inputPasswordValue.length != 6) {
                    Log.e("Rishabh","lenght: "+inputPasswordValue.length)
                    Log.e("Rishabh","value: "+inputPasswordValue)
                    showDialog(
                        "Error",
                        "Please enter valid password !!",
                        R.drawable.ic_alert_error_24dp
                    )
                } else {
                    val encodedKey = CryptoHash.getEncodedString(inputPasswordValue)
                    Log.e("Rishabh","Setting encoded key: "+encodedKey);
                    sharePref?.setLockPattern(encodedKey)
                    isPasswordSet = true
                    startActivity(Intent(this@SplashActivity, SplashActivity::class.java))
                    finish()
                }
            }
        }
    }
}
