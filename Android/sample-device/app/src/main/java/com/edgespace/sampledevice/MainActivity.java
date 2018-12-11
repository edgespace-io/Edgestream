package com.edgespace.sampledevice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import io.edgestream.edgesdk.EdgestreamClient;

public class MainActivity extends AppCompatActivity {

    EdgestreamClient _client;
    private String TAG = "Android Template";
    private TextView sentCount;
    private TextView lastMessage;

    /*  the below is the result from the RegisterDevice call that returns the below json structure the msg contains the token
     *
     *   {
     *       "KEY": "",
     *       "MSG": ""
     *   }
     *
     *
     * */
    private static String demoKey = "JLRYm8h68K5oNw4QnzA4QHIjYEA7hQNe2jEQJJf3t81/UdyaCSaRyqasFKtzJJK/+8snZKdMU/PJFt979vZG7M/srwRyEHa5yFKtJQrJ1d6cjWwFUtf4QMvRmJn6PJTWXntqAzDmg6TYYcfOtCPbXzW4KvACzFEJYFwV1S81HQY=";
    private static String demoToken = "yJWwbJJHgqOqJqGak+OePvFMquWvgOD7GmE29aPqAexvarUTbxpLSlwgtzDyfOEyrU+2YNj2CT8PXcaZK79uqj+b8xCPyG3QfDBzztsTaypzaSKZUmi7lGfV40LIg8EY9qVSkp19s4kQCA3jscWOPnpWCjjBuqYXhIz369jdnB+k2w3yzEdydujWYuswczoMoqPDsXqAWE5PFzILNKHWlfHArkP6R/ItRrlcGHqSJWP8Ekzxo2H96gRCqnPtElqY1SOzQBf6mcPJ09KwViveuxKDCxx+krEuJnyOEkoiX+Xb7BFb+e//zSazA7gbU24wRCQpdgb2wqycUOkXHL8D+UuVOTxCDiWpvmDQFHrdM85My3dsFKf05HWLGKBqjZ7mS3sNldFlk0emK6SPGei5Qg==";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sentCount = findViewById(R.id.lblMessagesSent);
        lastMessage = findViewById(R.id.lastMessage);
        try {
            _client = new EdgestreamClient(this.getApplicationContext());
            if(!_client.isRegistered()) {
                _client.setToken(demoKey, demoToken);
            }
        }catch(Exception ex){
            Log.e(TAG, ex.getMessage());
        }
    }

    public void btnStopOnClick(View view) {
        Log.i(TAG, "Stop Client Button Click");
        _client.iotDisconnect();
    }


    public void btnStartOnClick(View view) {
        Log.i(TAG, "Stop Client Button Click");

        try {
            if(_client.isRegistered()) {
                _client.iotConnect();
            }else{
                Log.w(TAG, "Device not registered");
            }
        }catch(Exception ex){
            Log.e(TAG, ex.getMessage());
        }
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
            sentCount.setText(""+ _client.getMessgesSent());
            lastMessage.setText(data.toString());
        }catch(Exception ex){
            Log.e(TAG, ex.getMessage());
        }
    }

}
