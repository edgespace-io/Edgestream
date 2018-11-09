package io.edgestream.edgesdk.samples.minclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONObject;

import io.edgestream.edgesdk.EdgestreamClient;


public class MainActivity extends AppCompatActivity {

    EdgestreamClient _client;
    private String TAG = "Android Template";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _client = new EdgestreamClient();
        try {
            _client.iotConnect(this.getApplicationContext());
        }catch(Exception ex){
            Log.e(TAG, ex.getMessage());
        }

    }

    public void btnStopOnClick(View view) {
        Log.i(TAG, "Stop Client Button Click");
        _client.iotDisconnect();
    }

    public void btnSendOnClick(View view) {
        Log.i(TAG, "Send Data Button Click");
        JSONObject data = new JSONObject();

        double temperature = 20.0 + Math.random() * 10;
        double humidity = 30.0 + Math.random() * 20;
        try {
            data.put("deviceId", _client.getDeviceId());
            data.put("temperature", temperature);
            data.put("humidity", humidity);

            _client.sendData(data);

        }catch(Exception ex){
            Log.e(TAG, ex.getMessage());
        }
    }

}