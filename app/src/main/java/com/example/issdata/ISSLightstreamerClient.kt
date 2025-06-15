package com.example.issdata

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import com.lightstreamer.client.ItemUpdate
import com.lightstreamer.client.LightstreamerClient
import com.lightstreamer.client.Subscription
import com.lightstreamer.client.SubscriptionListener

abstract class SimpleSubscriptionListener : SubscriptionListener {
    override fun onSubscription() {}
    override fun onUnsubscription() {}
    override fun onClearSnapshot(itemName: String?, itemPos: Int) {}
    override fun onCommandSecondLevelItemLostUpdates(lostUpdates: Int, key: String) {}
    override fun onCommandSecondLevelSubscriptionError(code: Int, message: String?, key: String?) {}
    override fun onEndOfSnapshot(itemName: String?, itemPos: Int) {}
    override fun onItemLostUpdates(itemName: String?, itemPos: Int, lostUpdates: Int) {}
    override fun onListenEnd() {}
    override fun onListenStart() {}
    override fun onRealMaxFrequency(frequency: String?) {}
    override fun onSubscriptionError(code: Int, message: String?) {}
}



object ISSLightstreamerClient {

    var floatValue by mutableFloatStateOf(0.0f)

    private val client = LightstreamerClient("https://push.lightstreamer.com", "ISSLIVE")
    private val items = arrayOf("NODE3000005")
    private val fields = arrayOf("TimeStamp", "Value")

    private val subscription = Subscription("MERGE", items, fields).apply {

        addListener(object : SimpleSubscriptionListener() {
            override fun onSubscription() {
                super.onSubscription()
                println("Subscribed")
            }

            override fun onSubscriptionError(code: Int, message: String?) {
                super.onSubscriptionError(code, message)
                Log.e(
                    "SUBSCRIPTION_ERROR",
                    """code : $code message: $message"""
                )
            }

            override fun onItemUpdate(obj: ItemUpdate) {
                val formatted = """${obj.getValue("TimeStamp")}: ${obj.getValue("Value")}%"""
                floatValue = (obj.getValue("Value")?.toFloat() ?: 0f) / 100
                println(formatted)
            }
        })
    }

    fun connect() {
        client.connect()
        client.subscribe(subscription)
    }

    fun disconnect() {
        client.unsubscribe(subscription)
        client.disconnect()
    }
}
