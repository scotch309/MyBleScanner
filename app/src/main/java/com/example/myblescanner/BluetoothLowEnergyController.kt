package com.example.myblescanner

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.*
import android.content.Context
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService

class BluetoothLowEnergyController(private val context: Context) {
    private var _scanning = false
    private val _backgroundHandler: Handler = Handler(Looper.getMainLooper())
    private val _bluetoothManager: BluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val _bluetoothAdapter: BluetoothAdapter = _bluetoothManager.adapter
    private val _bluetoothScanner: BluetoothLeScanner = _bluetoothAdapter.bluetoothLeScanner
    private val _bleScanCallback: BleScanCallback = BleScanCallback()

    fun startBLEDeviceScan() {
        if (_scanning) return
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) return
        _bluetoothScanner.startScan(buildScanFilters(), buildScanSettings(),_bleScanCallback)
        _scanning = true
    }
    fun stopBLEDeviceScan() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) return
        _bluetoothScanner.stopScan(_bleScanCallback)
        _bleScanCallback.clear()
        _scanning = false
    }
    private fun buildScanFilters(): List<ScanFilter> {
        val scanFilters: MutableList<ScanFilter> = ArrayList()
        val builder = ScanFilter.Builder()
        builder.setServiceUuid(null)
        scanFilters.add(builder.build())
        return scanFilters
    }
    private fun buildScanSettings(): ScanSettings {
        val builder = ScanSettings.Builder()
        builder.setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
        return builder.build()
    }
}

class BleScanCallback: ScanCallback() {
    private val resultList = mutableSetOf<ScanResult>()
    private val scanResultList = mutableListOf<ScanResult>()

    override fun onScanResult(callbackType: Int, result: ScanResult) {
        if (callbackType == ScanSettings.CALLBACK_TYPE_ALL_MATCHES) resultList.add(result)
    }
    override fun onBatchScanResults(results: MutableList<ScanResult>) {
        scanResultList.addAll(results)
    }
    fun clear() {
        resultList.clear()
        scanResultList.clear()
    }
}