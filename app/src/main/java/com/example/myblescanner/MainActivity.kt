package com.example.myblescanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private var _bleController: BluetoothLowEnergyController = BluetoothLowEnergyController(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 検索開始ボタン
        val btnScanStart: Button = findViewById<Button>(R.id.btn_scan_start)
        btnScanStart.setOnClickListener {
            btnScanStart.isClickable = false
            _bleController.startBLEDeviceScan()
        }
        // 検索終了ボタン
        val btnScanStop = findViewById<Button>(R.id.btn_scan_stop)
        btnScanStop.setOnClickListener {
            btnScanStop.isClickable = false
            _bleController.stopBLEDeviceScan()
            btnScanStop.isClickable = true
            btnScanStart.isClickable = true
        }
    }
}