package com.example.a10648.v2ex.net;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 10648 on 2016/7/15 0015.
 */
public class SendRequestWithHttpURLConnection {

    public static String sendRequestWithHttpURLConnection(){

        HttpURLConnection connection = null;
        try {
            URL url = new URL("https://www.v2ex.com/api/topics/latest.json");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            InputStream in = connection.getInputStream();


            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null){
                response.append(line);
            }
            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }finally {
            if (connection != null){
                connection.disconnect();
            }
        }


    }

}
