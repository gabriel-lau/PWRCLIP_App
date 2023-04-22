package com.example.pwrclipapp.mqtt

interface MQTTMessageCallback {
    public fun onMessageReceived(msg: Message)
}