package com.codingdenick.telephonydemo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import ir.siaray.telephonymanagerplus.TelephonyManagerPlus
import timber.log.Timber


open class MainActivity : AppCompatActivity() {

    private val REQUEST_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        setContentView(R.layout.activity_main)
        if (checkPermissions()) {
            readMultiSim()
        } else {
            requestPermissions()
        }
    }

    protected open fun readMultiSim() {
        val telephonyManagerPlus = TelephonyManagerPlus.getInstance(this)
        val imei1 = telephonyManagerPlus.imei1
        val imei2 = telephonyManagerPlus.imei2
        val simOperatorCode1 = telephonyManagerPlus.simOperatorCode1
        val simOperatorCode2 = telephonyManagerPlus.simOperatorCode2
        val simOperatorName1 = telephonyManagerPlus.simOperatorName1
        val simOperatorName2 = telephonyManagerPlus.simOperatorName2
        val simSerialNumber1 = telephonyManagerPlus.simSerialNumber1
        val simSerialNumber2 = telephonyManagerPlus.simSerialNumber2
        val subscriberId1 = telephonyManagerPlus.subscriberId1
        val subscriberId2 = telephonyManagerPlus.subscriberId2

        val mcc1 = telephonyManagerPlus.mcc1
        val mcc2 = telephonyManagerPlus.mcc2
        val mnc1 = telephonyManagerPlus.mnc1
        val mnc2 = telephonyManagerPlus.mnc2
        val cid1 = telephonyManagerPlus.cid1
        val cid2 = telephonyManagerPlus.cid2
        val lac1 = telephonyManagerPlus.lac1
        val lac2 = telephonyManagerPlus.lac2
        Timber.i("imsi_1:%s imsi_2:%s", subscriberId1, subscriberId2)
        Timber.i("imei_1:%s imei_2:%s", imei1, imei2)
        Timber.i("simOperatorCode1:%s simOperatorCode2:%s", simOperatorCode1, simOperatorCode2)
        Timber.i("simOperatorName1:%s simOperatorName2:%s", simOperatorName1, simOperatorName2)
        Timber.i("simSerialNumber1:%s simSerialNumber2:%s", simSerialNumber1, simSerialNumber2)
    }

    protected open fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
    }


    protected open fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_PHONE_STATE), REQUEST_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSION -> {
                var granted = true
                for (grant in grantResults) {
                    granted = grant == PackageManager.PERMISSION_GRANTED && granted
                }
                if (granted) {
                    readMultiSim()
                }
            }
            else -> throw NotImplementedError("方法未实现.")
        }
    }
}
