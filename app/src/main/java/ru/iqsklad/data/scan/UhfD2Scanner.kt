package ru.iqsklad.data.scan

import com.senter.iot.support.openapi.uhf.UhfD2
import ru.iqsklad.utils.extensions.convertToEPC

class UhfD2Scanner: Scanner() {

    override fun startInventoryTagInNeeded() {
        try {
            UhfD2.getInstance().init()
        } catch (e: IllegalStateException) {
            UhfD2.getInstance().reset()
        }
    }

    override fun stopInventory() {
        //UhfD2.getInstance().uninit()
        //scanner = null
    }

    override fun turnOff() {
        UhfD2.getInstance().uninit()
    }

    override fun getInventory() {
        UhfD2.getInstance().iso18k6cRealTimeInventory(1, object : UhfD2.UmdOnIso18k6cRealTimeInventory() {
            override fun onFinishedWithError(p0: UhfD2.UmdErrorCode?) {}

            override fun onFinishedSuccessfully(p0: Int?, p1: Int, p2: Int) {}

            override fun onTagInventory(uii: UhfD2.UII?, frequencyPoint: UhfD2.UmdFrequencyPoint?, antennaId: Int?, rssi: UhfD2.UmdRssi?) {
                uii?.convertToEPC()?.let { postInventory(it) }
            }
        })
    }
}