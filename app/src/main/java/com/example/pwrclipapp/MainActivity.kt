package com.example.pwrclipapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pwrclipapp.databinding.ActivityMainBinding
import com.example.pwrclipapp.mqtt.MQTTMessageCallback
import com.example.pwrclipapp.mqtt.Message
import com.example.pwrclipapp.ui.home.AppliancesViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hivemq.client.mqtt.datatypes.MqttQos
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
import java.nio.charset.StandardCharsets
import java.util.Date
import java.util.UUID
import java.util.function.Consumer
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val msgList: ArrayList<Message> = ArrayList<Message>()
    val data = ArrayList<AppliancesViewModel>()
    lateinit var msgCallback: MQTTMessageCallback
    var isCallbackEnabled: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data.add(AppliancesViewModel(R.drawable.microwave_48px, "Microwave", "Turned on at 12:00", "2kWh"))
        data.add(AppliancesViewModel(R.drawable.oven_48px, "Oven", "Turned on at 09:00", "8kWh"))
        data.add(AppliancesViewModel(R.drawable.fridge_48px, "Fridge", "Turned on at 3:00", "10kWh"))

        val navView: BottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navView.setupWithNavController(navController)
    }
    public fun enableMQTTSubscribe() {
        // MQTT Connection
        try {
            // attempt a connect
            val client = Mqtt5Client.builder()
                .identifier(UUID.randomUUID().toString())
                .serverHost("test.mosquitto.org")
                .serverPort(1883)
                .build()
            val connAckMessage = client.toBlocking().connect()
            //success
            connAckMessage.reasonCode.toString()
            client.toAsync()
                .subscribeWith()
                .topicFilter("pwrclip")
                .qos(MqttQos.EXACTLY_ONCE)
                .callback(object : Consumer<Mqtt5Publish> {
                    override fun accept(t: Mqtt5Publish) {
                        val payload: String = String(t.getPayloadAsBytes(), StandardCharsets.UTF_8)
                        val msg: Message = Message(t.getTopic().toString(), payload, Date())
                        msgList.add(msg)
                        if (isCallbackEnabled){
                            msgCallback.onMessageReceived(msg)
                        }
                    }
                })
                .send()
        } catch (e: Exception) {
            //failure
            e.message
        }
    }

    public fun connectMessageListener(fragmentMsgCallback: MQTTMessageCallback) {
        isCallbackEnabled = true
        msgCallback = fragmentMsgCallback
    }
    public fun disconnectMessageListener() {
        isCallbackEnabled = false
    }
}