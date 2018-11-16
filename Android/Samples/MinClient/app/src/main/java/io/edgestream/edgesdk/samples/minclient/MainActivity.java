package io.edgestream.edgesdk.samples.minclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONObject;

import io.edgestream.edgesdk.EdgestreamClient;


public class MainActivity extends AppCompatActivity {

    /* Define Edgestream Client Object */
    EdgestreamClient _client;

    private String TAG = "Android Template";

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
    private static String demoToken = "yJWwbJJHgqOqJqGak+OePvFMquWvgOD7GmE29aPqAexvarUTbxpLSlwgtzDyfOEyrU+2YNj2CT8PXcaZK79uqj+b8xCPyG3QfDBzztsTaypzaSKZUmi7lGfV40LIg8EY9qVSkp19s4kQCA3jscWOPnpWCjjBuqYXhIz369jdnB+k2w3yzEdydujWYuswczoMoqPDsXqAWE5PFzILNKHWlfHArkP6R/ItRrlcGHqSJWP8Ekzxo2H96gRCqnPtElqY1SOzQBf6mcPJ09KwViveuxKDCxx+krEuJnyOEkoiX+Xb7BFb+e//zSazA7gbU24wB5b+8ryLdSmdYxcPT9YQXmcigiHA5GgyBik479nu7/Tic/u5kppshqyBhhWZu3sFi8ybRzevk2xiLjuWE6ty+g==";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            /* Instantiate Edgestream Client with application context */
            _client = new EdgestreamClient(this.getApplicationContext());

            /* set the token received from Register Device API call on Edgestream Platform
            **** this is only required one time the SDK stores the information ****
             */
            _client.setToken(demoKey, demoToken);

            /* connect to platform for sending data */
            _client.iotConnect();

        }catch(Exception ex){
            Log.e(TAG, ex.getMessage());
        }

    }

    public void btnStopOnClick(View view) {
        Log.i(TAG, "Stop Client Button Click");

        /* disconnect from platform */
        _client.iotDisconnect();
    }

    public void btnSendOnClick(View view) {
        Log.i(TAG, "Send Data Button Click");
        JSONObject data = new JSONObject();

        double temperature = 20.0 + Math.random() * 10;
        double humidity = 30.0 + Math.random() * 20;
        try {
            data.put("deviceId", _client.getDeviceId());  /* retrieve device id assigned by platform */
            data.put("temperature", temperature);
            data.put("humidity", humidity);

            /* send data to platform in the form of a json object */
            _client.sendData(data);

        }catch(Exception ex){
            Log.e(TAG, ex.getMessage());
        }
    }

}