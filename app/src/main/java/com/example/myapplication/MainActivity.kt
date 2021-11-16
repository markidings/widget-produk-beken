package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel
import org.json.JSONObject
import org.json.JSONException


class MainActivity : AppCompatActivity() {
    lateinit var flutterEngine: FlutterEngine
    private val CHANNEL = "id.beken/message"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFlutterEngine()
        methodCallHandler()

        val buttonBeken = findViewById<Button>(R.id.buttonBeken)
        buttonBeken.setOnClickListener {
            startActivity(FlutterActivity.withCachedEngine("beken").build(this))
        }
    }

    fun setFlutterEngine() {
        flutterEngine = FlutterEngine(this)
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )
        FlutterEngineCache
            .getInstance()
            .put("beken", flutterEngine)
    }

    fun methodCallHandler() {
        val methodChannel =
            MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
        methodChannel.setMethodCallHandler { call, result ->

            if (call.method.equals("authentication")) {
                val json = JSONObject()
                try {
                    json.put(
                        "publicKey",
                        "d704891a0a06eb02a12545a9aaaa2c2ea63c42ff5da002a5292b52a5ec1bf0e3"
                    )
                    json.put(
                        "secretKey",
                        "2944b9ed8f2e9b3cee3a71ffb32623d3d3435ce1604039d6f673a134c5b5703a"
                    )
                    json.put("uuid", "TDYTwBHwMQMjVFEnNXHRPHnPf402")
                    json.put("email", "im.saipulhidayat@gmail.com")
                    result.success(json.toString())
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            if (call.method.equals("payment-callback")) {
                val data = call.argument<String>("data")

                result.success("success")
            }
        }
    }
}