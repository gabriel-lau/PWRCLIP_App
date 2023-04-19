package com.example.pwrclipapp

//import com.example.pwrclipapp.mqtt.MQTTClient
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pwrclipapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hivemq.client.mqtt.datatypes.MqttQos
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
import java.nio.charset.StandardCharsets
import java.util.UUID
import java.util.function.Consumer
import javax.security.auth.callback.CallbackHandler


//import org.eclipse.paho.client.mqttv3.IMqttActionListener
//import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
//import org.eclipse.paho.client.mqttv3.IMqttToken
//import org.eclipse.paho.client.mqttv3.MqttCallback
//import org.eclipse.paho.client.mqttv3.MqttMessage

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navView.setupWithNavController(navController)

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
            //client.toBlocking().publishWith().topic("pwrclip").qos(MqttQos.AT_LEAST_ONCE).payload("1".toByteArray()).send()
            client.toAsync()
                .subscribeWith()
                .topicFilter("pwrclip")
                .qos(MqttQos.EXACTLY_ONCE)
                .callback(object : Consumer<Mqtt5Publish> {
                    override fun accept(t: Mqtt5Publish) {
                        Log.d(TAG, "subscribe: callback: enter")
                        val string: String = String(t.getPayloadAsBytes(), StandardCharsets.UTF_8)
                        Log.d(TAG, "subscribe: callback: topic=" + t.getTopic() + "\n" + string)                    }
                }
            )
                .send()
        } catch (e: Exception) {
            //failure
            e.message
        }
    /*
        var mqttClient = MQTTClient(binding.root.context, "tcp://broker.hivemq.com:1883") // tcp://broker.hivemq.com:1883 // tcp://test.mosquitto.org:1883
        mqttClient.connect(
            "",
            "",
            object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(this.javaClass.name, "Connection success")

                    Toast.makeText(binding.root.context, "MQTT Connection success", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(this.javaClass.name, "Connection failure: ${exception.toString()}")

                    Toast.makeText(binding.root.context, "MQTT Connection fails: ${exception.toString()}",
                        Toast.LENGTH_SHORT).show()
                }
            },
            object : MqttCallback {
                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    val msg = "Receive message: ${message.toString()} from topic: $topic"
                    Log.d(this.javaClass.name, msg)

                    Toast.makeText(binding.root.context, msg, Toast.LENGTH_SHORT).show()
                }

                override fun connectionLost(cause: Throwable?) {
                    Log.d(this.javaClass.name, "Connection lost ${cause.toString()}")
                }

                override fun deliveryComplete(token: IMqttDeliveryToken?) {
                    Log.d(this.javaClass.name, "Delivery complete")
                }
            })
        val topic = "pWrCLIP"

        val topic = "pWrCLIP"
        mqttClient.subscribe(
            topic,
            1,
            object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    val msg = "Subscribed to: $topic"
                    Log.d(this.javaClass.name, msg)

                    Toast.makeText(binding.root.context, msg, Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(this.javaClass.name, "Failed to subscribe: $topic")
                }
            })    */
    }
}