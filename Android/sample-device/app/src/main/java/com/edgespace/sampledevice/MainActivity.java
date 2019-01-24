package com.edgespace.sampledevice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import io.edgestream.edgesdk.EdgestreamClient;
import io.edgestream.edgesdk.EdgestreamMessageEventCallback;


public class MainActivity extends AppCompatActivity implements EdgestreamMessageEventCallback {

    EdgestreamClient _client;
    private String TAG = "Android Template";
    private TextView sentCount;
    private TextView sendFailures;
    private TextView receiveCount;
    private TextView msgsConfirmed;
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
    private static String demoToken = "yJWwbJJHgqOqJqGak+OePvFMquWvgOD7GmE29aPqAexvarUTbxpLSlwgtzDyfOEyrU+2YNj2CT8PXcaZK79uql4OEbhjqeOQEkzX1jz/GkNnzQR/p0DSQnELkWumXusFo9bLdeKlOuaOkDo1mAHCrbJkWbaqyCmrT5U+k9a9ekB8LPdRdQhzkOGOlHh2fpFz/H9+ot0s+hwrhO7Loaa2FKInDhZkPh9ArtKh3GaWQC4JkNz5v5FCx1L9MswnHBLJOzD6Avs6YFKbf99GqQ1lRcLnlvR7GnT1dTwJXtV8FUky4IP7LWnlU7nGsYwOy6BdKxG+j9C/0R7aQw/KBqxn3hD8aPsRrxcZM0bsrymr37o=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sentCount = findViewById(R.id.lblMessagesSent);
        sendFailures = findViewById(R.id.lblSendFailures);
        receiveCount = findViewById(R.id.lblMessagesReceived);
        msgsConfirmed = findViewById(R.id.lblReceiptConfirmed);
        lastMessage = findViewById(R.id.lastMessage);
        try {
            _client = new EdgestreamClient(this.getApplicationContext());
            _client.RegisterEdgestreamMessageEventCallback(this);
            Log.d(TAG, "Event Message Callback Registered: " + _client.isEdgestreamMessageEventCallbackRegistered());
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

            if(_client.sendData(data)){
                sentCount.setText(""+ _client.getMessgesSent());
                lastMessage.setText(data.toString());
            }else {
                sendFailures.setText(Integer.toString(_client.getSendMessageFailures()));
            }
        }catch(Exception ex){
            Log.e(TAG, ex.getMessage());
        }
    }

    // Edgestream Client Callbacks
    @Override
    public void edgeEventCallback(final byte[] message) {
        // callback is from a background thread and needs to run on the UI thread to display
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), new String(message), Toast.LENGTH_SHORT).show();
                receiveCount.setText(Integer.toString(_client.getMessgesReceived()));
            }
        });
    }

    @Override
    public void sendMessageReceiptConfirmations(final int count) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                msgsConfirmed.setText(Integer.toString(count));
            }
        });
    }
}

