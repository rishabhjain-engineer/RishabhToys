package com.example.rishabhtoys

import android.util.Log
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

class CryptoHash {
    companion object{
        fun getEncodedString(input : String) : String{
            val messageDigest = MessageDigest.getInstance("SHA-256")
            messageDigest.update(input.toByteArray(StandardCharsets.UTF_16))
            val digest = messageDigest.digest()
            return java.lang.String.format("%064x", BigInteger(1, digest))
        }
    }

}